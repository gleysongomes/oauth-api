package io.github.gleysongomes.oauth.security;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

@Component
public class ApiSecurity {

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public boolean isAuthenticated() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			return authentication.isAuthenticated();
		}
		return false;
	}

	public boolean hasAuthority(String authorityName) {
		List<String> authorities = getAuthorities();
		if (authorities != null) {
			return authorities.stream().anyMatch(name -> name.equals(authorityName));
		}
		return false;
	}

	public List<String> getAuthorities() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			return authentication.getAuthorities().stream().map(authority -> authority.getAuthority()).toList();
		}
		return null;
	}

	public boolean temEscopoEscrita() {
		return hasScope("OAUTH_CLI_ESCRITA");
	}

	public boolean temEscopoLeitura() {
		return hasScope("OAUTH_CLI_LEITURA");
	}

	public boolean hasScope(String scope) {
		Set<String> scopes = getScopes();
		return scopes.contains(scope);
	}

	public Set<String> getScopes() {
		// @formatter:off
		return Optional.ofNullable(getAuthentication())
				.filter(OAuth2Authentication.class::isInstance)
				.map(OAuth2Authentication.class::cast)
				.map(auth -> auth.getOAuth2Request())
				.filter(Objects::nonNull)
				.map(auth -> auth.getScope())
				.orElse(Set.of());
		// @formatter:on
	}

	public String getLoginUsuarioLogado() {
		// @formatter:off
		return Optional.ofNullable(getAuthentication())
				.filter(OAuth2Authentication.class::isInstance)
				.map(OAuth2Authentication.class::cast)
				.map(auth -> (String) auth.getPrincipal())
				.orElse(null);
		// @formatter:on
	}

}
