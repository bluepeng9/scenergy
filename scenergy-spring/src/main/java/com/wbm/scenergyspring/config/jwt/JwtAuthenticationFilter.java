package com.wbm.scenergyspring.config.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * username, password post로 전송하면
 * UsernamePasswordAuthenticationFilter 동작
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	/**
	 * 로그인 요청시 로그인 시도를
	 * 위해서 실행되는 함수
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		System.out.println("Jwt 로그인 시도중");

		/**
		 * username, password 받아서
		 * 로그인 시도, authenticationManager로 로그인 시도시
		 * PrincipalDetailService 호출 loadUserByUsername 실행
		 * PrincipalDetails 세션에 담은 후(권한 관리) JWT 토큰 만들어서 응답
		 */
		return super.attemptAuthentication(request, response);
	}
}
