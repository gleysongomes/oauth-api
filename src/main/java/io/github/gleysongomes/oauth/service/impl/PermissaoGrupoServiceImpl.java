package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.input.PermissaoGrupoInput;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.PermissaoGrupo;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.model.primarykey.PermissaoGrupoId;
import io.github.gleysongomes.oauth.repository.PermissaoGrupoRepository;
import io.github.gleysongomes.oauth.service.GrupoService;
import io.github.gleysongomes.oauth.service.PermissaoGrupoService;
import io.github.gleysongomes.oauth.service.PermissaoService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class PermissaoGrupoServiceImpl implements PermissaoGrupoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PermissaoGrupoRepository permissaoGrupoRepository;

	private final UsuarioService usuarioService;

	private final GrupoService grupoService;

	private final PermissaoService permissaoService;

	public PermissaoGrupoServiceImpl(PermissaoGrupoRepository permissaoGrupoRepository, UsuarioService usuarioService,
			GrupoService grupoService, PermissaoService permissaoService) {
		this.permissaoGrupoRepository = permissaoGrupoRepository;
		this.usuarioService = usuarioService;
		this.grupoService = grupoService;
		this.permissaoService = permissaoService;
	}

	@Override
	@Transactional
	public PermissaoGrupo adicionar(PermissaoGrupo permissaoGrupo) {
		validarAssociacaoPermissaoGrupo(permissaoGrupo);

		permissaoService.buscar(permissaoGrupo.getCdPermissao());
		grupoService.buscar(permissaoGrupo.getCdGrupo());
		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(permissaoGrupo);

		try {
			permissaoGrupo.setDtCadastro(new Date());
			permissaoGrupo.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			permissaoGrupo = permissaoGrupoRepository.save(permissaoGrupo);
		} catch (Exception e) {
			log.debug("Erro ao associar permissão com grupo: {}.", permissaoGrupo);
			throw new ApiException("Erro ao associar permissão com grupo.", e);
		}
		return permissaoGrupo;
	}

	private void validarDuplicidade(PermissaoGrupo permissaoGrupo) {
		try {
			Optional<PermissaoGrupo> permissaoGrupoOptional = permissaoGrupoRepository
					.findById(new PermissaoGrupoId(permissaoGrupo.getCdPermissao(), permissaoGrupo.getCdGrupo()));

			if (permissaoGrupoOptional.isPresent()) {
				throw new ValidacaoException("Essa associação da permissão com o grupo já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Essa associação da permissão com o grupo já existe: {}.", permissaoGrupo);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação da permissão com o grupo: {}.", permissaoGrupo);
			throw new ApiException("Erro ao buscar associação da permissão com o grupo.", e);
		}
	}

	@Override
	@Transactional
	public PermissaoGrupo atualizar(PermissaoGrupo permissaoGrupo) {
		validarAssociacaoPermissaoGrupo(permissaoGrupo);

		permissaoService.buscar(permissaoGrupo.getCdPermissao());
		grupoService.buscar(permissaoGrupo.getCdGrupo());
		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			permissaoGrupo.setDtAtualizacao(new Date());
			permissaoGrupo.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			permissaoGrupo = permissaoGrupoRepository.save(permissaoGrupo);
		} catch (Exception e) {
			log.debug("Erro ao atualizar associação de permissão com grupo: {}.", permissaoGrupo.toString());
			throw new ApiException("Erro ao atualizar associação de permissão com grupo.", e);
		}
		return permissaoGrupo;
	}

	@Override
	public PermissaoGrupo buscar(Long cdPermissao, Long cdGrupo) {
		try {
			return permissaoGrupoRepository.findById(new PermissaoGrupoId(cdPermissao, cdGrupo))
					.orElseThrow(() -> new NaoEncontradoException("Associação da permissão com o grupo não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Associação da permissão {} com o grupo {} não encontrada.", cdPermissao, cdGrupo);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação da permissão {} com o grupo {}.", cdPermissao, cdGrupo);
			throw new ApiException("Erro ao buscar associação da permissão com o grupo.", e);
		}
	}

	@Override
	public List<PermissaoGrupo> findByCdPermissaoAndFlAtivaIsTrue(Long cdPermissao) {
		try {
			return permissaoGrupoRepository.findByCdPermissaoAndFlAtivaIsTrue(cdPermissao);
		} catch (Exception e) {
			log.debug("Erro ao listar grupos por permissão: {}.", cdPermissao);
			throw new ApiException("Erro ao listar grupos por permissão.", e);
		}
	}

	@Override
	public List<PermissaoGrupo> findByCdGrupoAndFlAtivaIsTrue(Long cdGrupo) {
		try {
			return permissaoGrupoRepository.findByCdGrupoAndFlAtivaIsTrue(cdGrupo);
		} catch (Exception e) {
			log.debug("Erro ao listar permissões por grupo: {}.", cdGrupo);
			throw new ApiException("Erro ao listar permissões por grupo.", e);
		}
	}

	private void validarAssociacaoPermissaoGrupo(PermissaoGrupo permissaoGrupo) {
		if (permissaoGrupo == null) {
			throw new ValidacaoException("Não existem informações para associar permissão com grupo.");
		}

		if (permissaoGrupo.getCdPermissao() == null) {
			throw new ValidacaoException("Informar código da permissão.");
		}

		if (permissaoGrupo.getCdGrupo() == null) {
			throw new ValidacaoException("Informar código do grupo.");
		}

		if (permissaoGrupo.getFlAtiva() == null) {
			throw new ValidacaoException("Informar se associação de permissão com grupo está ativa.");
		}
	}

	@Override
	public void validarAtualizacao(Long cdPermissao, Long cdGrupo, PermissaoGrupoInput permissaoGrupoInput) {
		if (!permissaoGrupoInput.getCdPermissao().equals(cdPermissao)) {
			throw new ValidacaoException(
					"O valor do código da permissão enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
		if (!permissaoGrupoInput.getCdGrupo().equals(cdGrupo)) {
			throw new ValidacaoException(
					"O valor do código do grupo enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
	}

}
