package com.wbm.scenergyspring.config;

import java.util.Date;

import org.springframework.web.util.UriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.http.HttpServletResponse;

//TODO: secret key 불러오는 방식 변경
public class JwtUtil {

	public static String generateJwtToken(Long userId, Long tokenExpirationMillis) {
		return JWT.create()
			.withSubject(userId.toString())
			.withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationMillis))
			.sign(Algorithm.HMAC512("webetterthanme"));
	}

	public static void addJwtTokenToResponseHeader(HttpServletResponse response, String jwtToken) {
		response.addHeader("Authorization", "Bearer " + jwtToken);
	}

	public static String addJwtTokenToUrl(String url, String queryParameterName, String jwtToken) {
		return UriComponentsBuilder.fromHttpUrl(url)
			.queryParam(queryParameterName, jwtToken)
			.build()
			.toUriString();
	}
}
