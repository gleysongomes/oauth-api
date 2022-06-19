package io.github.gleysongomes.oauth.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.gleysongomes.oauth.dto.input.AdicaoUsuarioInput;
import io.github.gleysongomes.oauth.dto.input.AtualizacaoUsuarioInput;
import io.github.gleysongomes.oauth.model.Usuario;

public abstract class UsuarioMapperDecorator implements UsuarioMapper {

	@Autowired
	@Qualifier("delegate")
	private UsuarioMapper delegate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Usuario toDomainObject(AdicaoUsuarioInput adicaoUsuarioInput) {
		Usuario usuario = delegate.toDomainObject(adicaoUsuarioInput);
		usuario.setHashSenha(passwordEncoder.encode(adicaoUsuarioInput.getSenha()));
		return usuario;
	}

	@Override
	public void copyToDomainObject(AtualizacaoUsuarioInput atualizacaoUsuarioInput, Usuario usuario) {
		delegate.copyToDomainObject(atualizacaoUsuarioInput, usuario);
		if (StringUtils.isNotBlank(atualizacaoUsuarioInput.getSenhaAtual())
				&& StringUtils.isNotBlank(atualizacaoUsuarioInput.getNovaSenha())
				&& StringUtils.isNotBlank(atualizacaoUsuarioInput.getNovaSenhaConfirmada())) {
			usuario.setHashSenha(passwordEncoder.encode(atualizacaoUsuarioInput.getNovaSenha()));
		}
	}

}
