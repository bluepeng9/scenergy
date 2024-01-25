package com.wbm.scenergyspring.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wbm.scenergyspring.config.auth.PrincipalDetails;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final UserRepository userRepository;
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	// 인증, 권한이 필요한 주소 요청시 실행
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		log.debug("권한 필요한 주소 요청");

		String jwtHeader = request.getHeader("Authorization");
		log.debug("========jwtHeader: "+jwtHeader);

		// header가 있는지 확인
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			chain.doFilter(request,response);
			return;
		}

		// JWT 토큰 검증 후 정상적인 사용자인지 확인
		String jwtToken = request.getHeader("Authorization").replace("Bearer","");
		String username =
			JWT.require(Algorithm.HMAC512("webetterthanme")).build().verify(jwtToken).getClaim("username").asString();

		log.debug("jwt토큰"+jwtToken);
		// 서명이 정상적으로 진행된 경우
		if (username != null) {
			User userEntity = userRepository.findByUsername(username);
			log.debug("userEntity++"+userEntity);
			PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

			// JWT 토큰 서명을 통해서 정상이면 authentication 객체 만들어줌
			Authentication authentication =
				new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());
			log.debug("authentication====="+authentication);

			// 강제로 시큐릴티 세션에 접근해서 Authentication 객체 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		chain.doFilter(request, response);
	}
}
