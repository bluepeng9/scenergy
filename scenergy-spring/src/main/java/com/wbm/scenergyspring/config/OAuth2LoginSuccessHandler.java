package com.wbm.scenergyspring.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.wbm.scenergyspring.config.auth.PrincipalDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	final String redirectUrl;
	final Long tokenExpirationMillis = 1000L * 60 * 60 * 24 * 30;

	public OAuth2LoginSuccessHandler(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		PrincipalDetails principal = (PrincipalDetails)authentication.getPrincipal();
		String token = JwtUtil.generateJwtToken(principal.getUser().getId(), tokenExpirationMillis);
		log.info("redirectUrl: " + redirectUrl);
		response.sendRedirect(JwtUtil.addJwtTokenToUrl(redirectUrl, "token", token));
		super.clearAuthenticationAttributes(request);
	}
}
