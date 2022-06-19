package io.github.gleysongomes.oauth.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import io.github.gleysongomes.oauth.model.Aplicacao;
import io.github.gleysongomes.oauth.model.Autoridade;
import io.github.gleysongomes.oauth.model.Escopo;
import io.github.gleysongomes.oauth.model.Recurso;
import io.github.gleysongomes.oauth.model.Redirecionamento;
import io.github.gleysongomes.oauth.model.resumo.TipoGrantAplicacaoResumo;
import io.github.gleysongomes.oauth.service.AplicacaoService;

@Service
public class ApiClientDetailsService implements ClientDetailsService {

	private final AplicacaoService aplicacaoService;

	private final PasswordEncoder passwordEncoder;

	public ApiClientDetailsService(AplicacaoService aplicacaoService, PasswordEncoder passwordEncoder) {
		this.aplicacaoService = aplicacaoService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		Aplicacao aplicacao = aplicacaoService.buscarPorNome(clientId);

		BaseClientDetails clientDetails = new BaseClientDetails();

		clientDetails.setClientId(aplicacao.getNome());
		clientDetails.setClientSecret(passwordEncoder.encode(aplicacao.getSegredo()));
		clientDetails.setAccessTokenValiditySeconds(aplicacao.getTmpAccessToken());
		clientDetails.setRefreshTokenValiditySeconds(aplicacao.getTmpRefreshToken());
		clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes(aplicacao.getTiposGrant()));
		clientDetails.setScope(scopes(aplicacao.getEscopos()));
		clientDetails.setAuthorities(authorities(aplicacao.getAutoridades()));
		clientDetails.setResourceIds(resourceIds(aplicacao.getRecursos()));
		clientDetails.setRegisteredRedirectUri(webServerRedirectUri(aplicacao.getRedirecionamentos()));

		return clientDetails;
	}

	private Set<String> webServerRedirectUri(Set<Redirecionamento> redirecionamentos) {
		if (redirecionamentos != null && !redirecionamentos.isEmpty()) {
			return redirecionamentos.stream().filter(r -> r != null && StringUtils.isNotBlank(r.getUrl()))
					.map(r -> r.getUrl()).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}

	private Collection<String> resourceIds(Set<Recurso> recursos) {
		if (recursos != null && !recursos.isEmpty()) {
			return recursos.stream().filter(r -> r != null).map(r -> r.getNome()).collect(Collectors.toSet());
		}
		return Collections.emptyList();
	}

	private Collection<? extends GrantedAuthority> authorities(Set<Autoridade> autoridades) {
		if (autoridades != null && !autoridades.isEmpty()) {
			return autoridades.stream().filter(a -> a != null && StringUtils.isNotBlank(a.getNome()))
					.map(a -> new SimpleGrantedAuthority(a.getNome().toUpperCase())).collect(Collectors.toSet());
		}
		return Collections.emptyList();
	}

	private Collection<String> scopes(Set<Escopo> escopos) {
		if (escopos != null && !escopos.isEmpty()) {
			return escopos.stream().filter(e -> e != null && StringUtils.isNotBlank(e.getNome())).map(e -> e.getNome())
					.collect(Collectors.toSet());
		}
		return Collections.emptyList();
	}

	private Collection<String> authorizedGrantTypes(Set<TipoGrantAplicacaoResumo> tipos) {
		if (tipos != null && !tipos.isEmpty()) {
			return tipos.stream()
					.filter(t -> t != null && t.getTipoGrant() != null && StringUtils.isNotBlank(t.getTipoGrant().getNome()))
					.map(t -> t.getTipoGrant().getNome()).collect(Collectors.toSet());
		}
		return Collections.emptyList();
	}

}
