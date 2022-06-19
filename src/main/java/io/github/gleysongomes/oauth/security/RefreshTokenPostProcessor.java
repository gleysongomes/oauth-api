package io.github.gleysongomes.oauth.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import io.github.gleysongomes.oauth.model.Aplicacao;
import io.github.gleysongomes.oauth.service.AplicacaoService;
import io.github.gleysongomes.oauth.util.ConstantesUtil;
import io.github.gleysongomes.oauth.webservice.helper.ResourceUriHelper;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	private final AplicacaoService aplicacaoService;

	public RefreshTokenPostProcessor(AplicacaoService aplicacaoService) {
		this.aplicacaoService = aplicacaoService;
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {

		HttpServletRequest req = ResourceUriHelper.getHttpServletRequest();

		String nome = req.getParameter(ConstantesUtil.NOME_APP_PARAMETER_REQUEST);

		Aplicacao aplicacao = aplicacaoService.buscarPorNome(nome);

		if (Boolean.FALSE.equals(aplicacao.getFlRefreshToken())) {
			DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
			token.setRefreshToken(null);
		}

		return body;
	}

}
