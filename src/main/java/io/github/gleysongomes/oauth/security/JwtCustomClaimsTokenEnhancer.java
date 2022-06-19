package io.github.gleysongomes.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		if (authentication.getPrincipal() instanceof ApiUser) {
			ApiUser authUser = (ApiUser) authentication.getPrincipal();

			Map<String, Object> info = new HashMap<>();
			info.put("login_usuario", authUser.getLoginUsuario());
			info.put("email_usuario", authUser.getEmailUsuario());
			info.put("nm_usuario", authUser.getNmUsuario());

			DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
			defaultOAuth2AccessToken.setAdditionalInformation(info);
		}

		return accessToken;
	}

}
