package com.wbm.scenergyspring.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbm.scenergyspring.config.auth.PrincipalDetails;
import com.wbm.scenergyspring.domain.user.entity.User;

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

		// try {
		// 	BufferedReader br = request.getReader();
		// 	String input = null;
		// 	while ((input = br.readLine()) != null) {
		// 		System.out.println("****8input*****"+input);
		// 	}
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }

		try {
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(),User.class);
			System.out.println(user);

			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			// PrincipalDetailService의 loadUserByUsername() 함수 실행
			Authentication authentication =
				authenticationManager.authenticate(authenticationToken);
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			System.out.println("********"+principalDetails.getUser().getUsername());
			return authentication;
		} catch (IOException e) {
			e.printStackTrace( );
		}


		// try {
		// 	// stream에 username, password 담겨있다
		// 	System.out.println(request.getInputStream().toString());
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }
		return null;
	}
}
