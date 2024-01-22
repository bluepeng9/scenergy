package com.wbm.scenergyspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authorizeRequests) ->
				authorizeRequests
					.requestMatchers("/user/**").authenticated()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().permitAll()
			);
			// .and()
			// .formLogin()
			// .loginPage("/loginForm")
			// .loginProcessingUrl("/login")
			// .defaultSuccessUrl("/")
			// .and()
			// .oauth2Login()
			// .loginPage("/loginForm");

		return http.build();
	}
}