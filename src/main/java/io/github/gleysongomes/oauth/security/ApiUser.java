package io.github.gleysongomes.oauth.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import io.github.gleysongomes.oauth.model.Usuario;
import lombok.Getter;

@Getter
public class ApiUser extends User {

	private static final long serialVersionUID = 7113542429463037642L;

	private Long cdUsuario;

	private String nmUsuario;

	private String loginUsuario;

	private String emailUsuario;

	public ApiUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getLogin(), usuario.getHashSenha(), authorities);

		this.cdUsuario = usuario.getCdUsuario();
		this.loginUsuario = usuario.getLogin();
		this.emailUsuario = usuario.getEmail();
		this.nmUsuario = usuario.getNome();
	}

}
