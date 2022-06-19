package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.input.PermissaoUsuarioInput;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.PermissaoUsuario;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.model.primarykey.PermissaoUsuarioId;
import io.github.gleysongomes.oauth.repository.PermissaoUsuarioRepository;
import io.github.gleysongomes.oauth.service.PermissaoService;
import io.github.gleysongomes.oauth.service.PermissaoUsuarioService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class PermissaoUsuarioServiceImpl implements PermissaoUsuarioService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PermissaoUsuarioRepository permissaoUsuarioRepository;

	private final PermissaoService permissaoService;

	private final UsuarioService usuarioService;

	public PermissaoUsuarioServiceImpl(PermissaoUsuarioRepository permissaoUsuarioRepository,
			PermissaoService permissaoService, UsuarioService usuarioService) {
		this.permissaoUsuarioRepository = permissaoUsuarioRepository;
		this.permissaoService = permissaoService;
		this.usuarioService = usuarioService;
	}

	@Override
	@Transactional
	public PermissaoUsuario adicionar(PermissaoUsuario permissaoUsuario) {
		validarAssociacaoPermissaoUsuario(permissaoUsuario);

		permissaoService.buscar(permissaoUsuario.getCdPermissao());
		usuarioService.buscar(permissaoUsuario.getCdUsuario());
		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(permissaoUsuario);

		try {
			permissaoUsuario.setDtCadastro(new Date());
			permissaoUsuario.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			permissaoUsuario = permissaoUsuarioRepository.save(permissaoUsuario);
		} catch (Exception e) {
			log.debug("Erro ao associar permissão com usuário: {}.", permissaoUsuario);
			throw new ApiException("Erro ao associar permissão com usuário.", e);
		}
		return permissaoUsuario;
	}

	private void validarDuplicidade(PermissaoUsuario permissaoUsuario) {
		try {
			Optional<PermissaoUsuario> permissaoUsuarioOptional = permissaoUsuarioRepository
					.findById(new PermissaoUsuarioId(permissaoUsuario.getCdPermissao(), permissaoUsuario.getCdUsuario()));

			if (permissaoUsuarioOptional.isPresent()) {
				throw new ValidacaoException("Essa associação da permissão com o usuário já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Essa associação da permissão com o usuário já existe: {}.", permissaoUsuario);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação da permissão com o usuário: {}.", permissaoUsuario);
			throw new ApiException("Erro ao buscar associação da permissão com o usuário.", e);
		}
	}

	@Override
	@Transactional
	public PermissaoUsuario atualizar(PermissaoUsuario permissaoUsuario) {
		validarAssociacaoPermissaoUsuario(permissaoUsuario);

		permissaoService.buscar(permissaoUsuario.getCdPermissao());
		usuarioService.buscar(permissaoUsuario.getCdUsuario());
		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			permissaoUsuario.setDtAtualizacao(new Date());
			permissaoUsuario.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			permissaoUsuario = permissaoUsuarioRepository.save(permissaoUsuario);
		} catch (Exception e) {
			log.debug("Erro ao atualizar associação de permissão com usuário: {}.", permissaoUsuario);
			throw new ApiException("Erro ao atualizar associação da permissão com o usuário.", e);
		}
		return permissaoUsuario;
	}

	@Override
	public PermissaoUsuario buscar(Long cdPermissao, Long cdUsuario) {
		try {
			return permissaoUsuarioRepository.findById(new PermissaoUsuarioId(cdPermissao, cdUsuario))
					.orElseThrow(() -> new NaoEncontradoException("Associação da permissão com o usuário não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Associação da permissão {} com o usuário {} não encontrada.", cdPermissao, cdUsuario);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação da permissão {} com o usuário {}.", cdPermissao, cdUsuario);
			throw new ApiException("Erro ao buscar associação da permissão com o usuário.", e);
		}
	}

	@Override
	public List<PermissaoUsuario> findByCdPermissaoAndFlAtivaIsTrue(Long cdPermissao) {
		try {
			return permissaoUsuarioRepository.findByCdPermissaoAndFlAtivaIsTrue(cdPermissao);
		} catch (Exception e) {
			log.debug("Erro ao listar usuários por permissão: {}.", cdPermissao);
			throw new ApiException("Erro ao listar usuários por permissão.", e);
		}
	}

	@Override
	public List<PermissaoUsuario> findByCdUsuarioAndFlAtivaIsTrue(Long cdUsuario) {
		try {
			return permissaoUsuarioRepository.findByCdUsuarioAndFlAtivaIsTrue(cdUsuario);
		} catch (Exception e) {
			log.debug("Erro ao listar permissões por usuário: {}.", cdUsuario);
			throw new ApiException("Erro ao listar permissões por usuário.", e);
		}
	}

	private void validarAssociacaoPermissaoUsuario(PermissaoUsuario permissaoUsuario) {
		if (permissaoUsuario == null) {
			throw new ValidacaoException("Não existem informações para associar permissão com usuário.");
		}

		if (permissaoUsuario.getCdPermissao() == null) {
			throw new ValidacaoException("Informar código da permissão.");
		}

		if (permissaoUsuario.getCdUsuario() == null) {
			throw new ValidacaoException("Informar código do usuário.");
		}

		if (permissaoUsuario.getFlAtiva() == null) {
			throw new ValidacaoException("Informar se associação de permissão com usuário está ativa.");
		}
	}

	@Override
	public void validarAtualizacao(Long cdPermissao, Long cdUsuario, PermissaoUsuarioInput permissaoUsuarioInput) {
		if (!permissaoUsuarioInput.getCdPermissao().equals(cdPermissao)) {
			throw new ValidacaoException(
					"O valor do código da permissão enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
		if (!permissaoUsuarioInput.getCdUsuario().equals(cdUsuario)) {
			throw new ValidacaoException(
					"O valor do código do usuário enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
	}

	@Override
	public List<PermissaoUsuario> findByCdUsuarioAndFlAtivaIsTrueAndNomesApis(Long cdUsuario, String nomesApis) {
		try {
			return permissaoUsuarioRepository.findByCdUsuarioAndFlAtivaIsTrueAndNomesApis(cdUsuario, nomesApis);
		} catch (Exception e) {
			log.debug("Erro ao listar permissões por código do usuário {} e nomes das aplicações: {}.", cdUsuario,
					nomesApis);
			throw new ApiException("Erro ao listar permissões por código do usuário e nomes das aplicações.", e);
		}
	}

}
