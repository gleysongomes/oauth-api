package io.github.gleysongomes.oauth.webservice.authorize;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface PermissaoAuthorize {

	@PreAuthorize("@apiSecurity.temEscopoLeitura() and hasAuthority('LISTAR_PERMISSAO')")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface Listar {
	}

	@PreAuthorize("@apiSecurity.temEscopoLeitura() and hasAuthority('RELATORIO_PERMISSAO')")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface Relatorio {
	}

	@PreAuthorize("@apiSecurity.temEscopoLeitura() and hasAuthority('BUSCAR_PERMISSAO')")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface Buscar {
	}

	@PreAuthorize("@apiSecurity.temEscopoEscrita() and hasAuthority('ADICIONAR_PERMISSAO')")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface Adicionar {
	}

	@PreAuthorize("@apiSecurity.temEscopoEscrita() and hasAuthority('ATUALIZAR_PERMISSAO') and (@permissaoServiceImpl.isOwner(#cdPermissao) or hasAuthority('GRUPO_ADMIN'))")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface Atualizar {
	}

}
