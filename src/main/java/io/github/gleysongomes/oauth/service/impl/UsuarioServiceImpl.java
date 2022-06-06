package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.UsuarioMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.UsuarioRepository;
import io.github.gleysongomes.oauth.service.UsuarioService;
import io.github.gleysongomes.oauth.util.ObjectMapperUtil;

@Service
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	@Transactional
	public Usuario adicionar(Usuario usuario) {
		Usuario usuarioCriacao = usuarioRepository.findById(1L)
				.orElseThrow(() -> new NaoEncontradoException("Usuário de criação não encontrado."));

		try {
			usuario.setDtCadastro(new Date());
			usuario.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			usuario = usuarioRepository.save(usuario);
		} catch (Exception e) {
			log.debug("Erro ao adicionar usuário: {}.", ObjectMapperUtil.writeValueAsString(usuario));
			throw new ApiException("Erro ao adicionar usuário.", e);
		}
		return usuario;
	}

	@Override
	public void validarAdicaoUsuario(Usuario usuario) {
		if (usuario == null) {
			throw new ValidacaoException("Informe um usuário.");
		}

		Optional<Usuario> usuarioLogin = usuarioRepository.findByLogin(usuario.getLogin());

		if (usuarioLogin.isPresent()) {
			throw new ValidacaoException("Esse login já existe.");
		}

		Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());

		if (usuarioEmail.isPresent()) {
			throw new ValidacaoException("Esse email já existe.");
		}
	}

	@Override
	@Transactional
	public Usuario atualizar(Usuario usuario) {
		Usuario usuarioAtualizacao = usuarioRepository.findById(1L)
				.orElseThrow(() -> new NaoEncontradoException("Usuário de atualização não encontrado."));

		try {
			usuario.setDtAtualizacao(new Date());
			usuario.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			usuario = usuarioRepository.save(usuario);
		} catch (Exception e) {
			log.debug("Erro ao atualizar usuário: {}.", ObjectMapperUtil.writeValueAsString(usuario));
			throw new ApiException(String.format("Erro ao atualizar usuário com o código: %s.", usuario.getCdUsuario()), e);
		}
		return usuario;
	}

	@Override
	public void validarAtualizacaoUsuario(Usuario usuario) {
		if (usuario == null) {
			throw new ValidacaoException("Informe um usuário.");
		}

		Optional<Usuario> usuarioLogin = usuarioRepository.findByLogin(usuario.getLogin());

		if (usuarioLogin.isPresent() && !usuarioLogin.get().getCdUsuario().equals(usuario.getCdUsuario())) {
			throw new ValidacaoException("Esse login já existe.");
		}

		Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());

		if (usuarioEmail.isPresent() && !usuarioEmail.get().getCdUsuario().equals(usuario.getCdUsuario())) {
			throw new ValidacaoException("Esse email já existe.");
		}
	}

	@Override
	public PageDTO<UsuarioMapping> listar(UsuarioFilter usuarioFilter, PageDTO<UsuarioMapping> pageDTO) {
		try {
			List<UsuarioMapping> usuarios = usuarioRepository.listar(usuarioFilter, pageDTO);

			Long total = usuarioRepository.contar(usuarioFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, usuarios);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de usuários com os filtros: {} e paginação: {}.",
					ObjectMapperUtil.writeValueAsString(usuarioFilter), ObjectMapperUtil.writeValueAsString(pageDTO));
			throw new ApiException("Erro ao buscar lista de usuários.", e);
		}
	}

	@Override
	public Usuario buscar(Long cdUsuario) {
		try {
			return usuarioRepository.findById(cdUsuario)
					.orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado."));
		} catch (Exception e) {
			log.debug("Erro ao buscar usuário com o código: {}.", cdUsuario);
			throw new ApiException(String.format("Erro ao buscar usuário com o código: %s.", cdUsuario), e);
		}
	}

	@Override
	public void validarAtualizacaoSenha(String senhaAtual, String novaSenha, String novaSenhaConfirmada) {
		if (StringUtils.isNotBlank(senhaAtual) || StringUtils.isNotBlank(novaSenha)
				|| StringUtils.isNotBlank(novaSenhaConfirmada)) {
			if (StringUtils.isBlank(senhaAtual)) {
				throw new ValidacaoException("Informe a senha atual.");
			}
			if (StringUtils.isBlank(novaSenha)) {
				throw new ValidacaoException("Informe a nova senha.");
			}
			if (StringUtils.isBlank(novaSenhaConfirmada)) {
				throw new ValidacaoException("Falta confirmar a nova senha.");
			}
			if (!novaSenha.equals(novaSenhaConfirmada)) {
				throw new ValidacaoException("A nova senha é diferente da senha confirmada.");
			}
		}
	}

	@Override
	public void validarSenhaAtual(String senhaAtual, String hashSenha) {
		if (StringUtils.isNotBlank(senhaAtual) && StringUtils.isNotBlank(hashSenha)) {
			if (!senhaAtual.equals(hashSenha)) {
				throw new ValidacaoException("A senha atual está incorreta.");
			}
		}
	}

	@Override
	public void validarConfirmacaoSenha(String senha, String senhaConfirmada) {
		if (!senha.equals(senhaConfirmada)) {
			throw new ValidacaoException("A senha é diferente da senha confirmada.");
		}
	}

}
