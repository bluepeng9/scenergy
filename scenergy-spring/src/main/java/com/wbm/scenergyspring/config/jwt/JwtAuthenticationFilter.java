package com.wbm.scenergyspring.config.jwt;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbm.scenergyspring.config.auth.PrincipalDetails;
import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * username, password post로 전송하면
 * UsernamePasswordAuthenticationFilter 동작
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	/**
	 * 로그인 요청시 로그인 시도를
	 * 위해서 실행되는 함수
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		try {
			User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

			return authenticationManager.authenticate(authenticationToken);
		} catch (IOException e) {
			log.error("JwtAuthenticationFilter: " + e.getMessage());
		}

		return null;
	}

	/**
	 * attemptAuthentication 실행 후 인증이 정상적으로 완료되면
	 * successfulAuthentication 함수 실행
	 * JWT 토큰 만들어서 request 요청한 사용자한테 리턴
	 */

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) {
		log.debug("successfulAuthentication실행 인증완료 !!!");
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();

		String jwtToken = JWT.create()
			.withSubject("webetterthanme 토큰")
			.withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10)))
			.withClaim("id", principalDetails.getUser().getId())
			.withClaim("username", principalDetails.getUser().getUsername())
			.sign(Algorithm.HMAC512("webetterthanme"));
		response.addHeader("Authorization", "Bearer" + jwtToken);
	}
}
