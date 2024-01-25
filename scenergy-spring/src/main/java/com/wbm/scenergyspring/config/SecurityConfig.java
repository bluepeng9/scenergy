package com.wbm.scenergyspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import com.wbm.scenergyspring.config.jwt.JwtAuthenticationFilter;
import com.wbm.scenergyspring.config.jwt.JwtAuthorizationFilter;
import com.wbm.scenergyspring.config.oauth.PrincipalOauth2UserService;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private final PrincipalOauth2UserService principalOauth2UserService;

	private final CorsFilter corsFilter;
	private final UserRepository userRepository;

	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			// .apply(new MyCustomDsl())
			.authorizeHttpRequests(authorizeHttpRequests ->
				authorizeHttpRequests
					.requestMatchers("/user/**").authenticated()
					.requestMatchers("/admin/**").hasRole("admin")
					.anyRequest().permitAll()
			)
			.oauth2Login(oauth2Login ->
				oauth2Login
					.loginPage("/login")
					.userInfoEndpoint(userInfo -> userInfo.userService(principalOauth2UserService))
			)
			.with(new MyCustomDsl(), Customizer.withDefaults());

		return http.build();
	}

	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
				.addFilter(corsFilter)
				.addFilter(new JwtAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)))
				.addFilter(new JwtAuthorizationFilter(http.getSharedObject(AuthenticationManager.class), userRepository));
		}
	}
}
