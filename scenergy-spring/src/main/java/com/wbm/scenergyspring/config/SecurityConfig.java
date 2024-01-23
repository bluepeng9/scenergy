package com.wbm.scenergyspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import com.wbm.scenergyspring.config.oauth.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 등록
@RequiredArgsConstructor
public class SecurityConfig  {

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

	private final CorsFilter corsFilter;
	@Bean // 패스워드 암호화
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(corsFilter)
			.formLogin().disable()
			.httpBasic().disable()
			.authorizeRequests()
			.requestMatchers( "/user/**").authenticated()
			.requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.oauth2Login()
			.loginPage("/loginForm")
			// .loginProcessingUrl("/loginForm")
			// // 로그인 완료시 원하는 페이지로 보내줌
			// .defaultSuccessUrl("/")

			// 로그인 완료 후 후처리
			// 코드받기(정상로그인,인증) -> 엑세스토큰(권한) ->
			// 사용자 프로필 정보 가져옴 -> 이를 토대로 자동회원가입
			.userInfoEndpoint()
			.userService(principalOauth2UserService);

		return http.build();
	}

}
