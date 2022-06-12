package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.GrupoUsuario;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.model.primarykey.GrupoUsuarioId;
import io.github.gleysongomes.oauth.repository.GrupoUsuarioRepository;
import io.github.gleysongomes.oauth.service.GrupoService;
import io.github.gleysongomes.oauth.service.GrupoUsuarioService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class GrupoUsuarioServiceImpl implements GrupoUsuarioService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final GrupoUsuarioRepository grupoUsuarioRepository;

	private final UsuarioService usuarioService;

	private final GrupoService grupoService;

	public GrupoUsuarioServiceImpl(GrupoUsuarioRepository grupoUsuarioRepository, UsuarioService usuarioService,
			GrupoService grupoService) {
		this.grupoUsuarioRepository = grupoUsuarioRepository;
		this.usuarioService = usuarioService;
		this.grupoService = grupoService;
	}

	@Override
	@Transactional
	public GrupoUsuario adicionar(GrupoUsuario grupoUsuario) {
		validarAssociacaoGrupoUsuario(grupoUsuario);

		grupoService.buscar(grupoUsuario.getCdUsuario());
		usuarioService.buscar(grupoUsuario.getCdUsuario());
		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(grupoUsuario);

		try {
			grupoUsuario.setDtCadastro(new Date());
			grupoUsuario.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			grupoUsuario = grupoUsuarioRepository.save(grupoUsuario);
		} catch (Exception e) {
			log.debug("Erro ao associar o grupo com o usuário: {}.", grupoUsuario);
			throw new ApiException("Erro ao associar o grupo com o usuário.", e);
		}
		return grupoUsuario;
	}

	private void validarDuplicidade(GrupoUsuario grupoUsuario) {
		try {
			Optional<GrupoUsuario> grupoUsuarioOptional = grupoUsuarioRepository
					.findById(new GrupoUsuarioId(grupoUsuario.getCdGrupo(), grupoUsuario.getCdUsuario()));

			if (grupoUsuarioOptional.isPresent()) {
				throw new ValidacaoException("Essa associação do grupo com o usuário já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Essa associação do grupo com o usuário já existe: {}.", grupoUsuario);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação do grupo com o usuário: {}.", grupoUsuario);
			throw new ApiException("Erro ao buscar associação do grupo com o usuário.", e);
		}
	}

	@Override
	@Transactional
	public GrupoUsuario atualizar(GrupoUsuario grupoUsuario) {
		validarAssociacaoGrupoUsuario(grupoUsuario);

		grupoService.buscar(grupoUsuario.getCdUsuario());
		usuarioService.buscar(grupoUsuario.getCdUsuario());
		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			grupoUsuario.setDtAtualizacao(new Date());
			grupoUsuario.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			grupoUsuario = grupoUsuarioRepository.save(grupoUsuario);
		} catch (Exception e) {
			log.debug("Erro ao atualizar associação do grupo com o usuário: {}.", grupoUsuario);
			throw new ApiException("Erro ao atualizar associação do grupo com o usuário.", e);
		}
		return grupoUsuario;
	}

	@Override
	public GrupoUsuario buscar(Long cdGrupo, Long cdUsuario) {
		try {
			return grupoUsuarioRepository.findById(new GrupoUsuarioId(cdGrupo, cdUsuario))
					.orElseThrow(() -> new NaoEncontradoException("Associação do grupo com o usuário não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Associação do grupo {} com o usuário {} não encontrada.", cdGrupo, cdUsuario);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação do grupo {} com o usuário {}.", cdGrupo, cdUsuario);
			throw new ApiException("Erro ao buscar associação do grupo com o usuário.", e);
		}
	}

	@Override
	public List<GrupoUsuario> findByCdGrupoAndFlAtivoIsTrue(Long cdGrupo) {
		try {
			return grupoUsuarioRepository.findByCdGrupoAndFlAtivoIsTrue(cdGrupo);
		} catch (Exception e) {
			log.debug("Erro ao listar usuários por grupo: {}.", cdGrupo);
			throw new ApiException("Erro ao listar usuários por grupo.", e);
		}
	}

	@Override
	public List<GrupoUsuario> findByCdUsuarioAndFlAtivoIsTrue(Long cdUsuario) {
		try {
			return grupoUsuarioRepository.findByCdUsuarioAndFlAtivoIsTrue(cdUsuario);
		} catch (Exception e) {
			log.debug("Erro ao listar grupos por usuário: {}.", cdUsuario);
			throw new ApiException("Erro ao listar grupos por usuário.", e);
		}
	}

	private void validarAssociacaoGrupoUsuario(GrupoUsuario grupoUsuario) {
		if (grupoUsuario == null) {
			throw new ValidacaoException("Não existem informações para associar grupo com usuário.");
		}

		if (grupoUsuario.getCdGrupo() == null) {
			throw new ValidacaoException("Informar código do grupo.");
		}

		if (grupoUsuario.getCdUsuario() == null) {
			throw new ValidacaoException("Informar código do usuário.");
		}

		if (grupoUsuario.getFlAtivo() == null) {
			throw new ValidacaoException("Informar se associação de grupo com usuário está ativa.");
		}
	}

}
