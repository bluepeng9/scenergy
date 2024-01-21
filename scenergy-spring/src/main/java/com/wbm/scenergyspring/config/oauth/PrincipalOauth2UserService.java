package com.wbm.scenergyspring.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	/**
	 *
	 * Access Token 얻고나서 실행
	 * 토큰과 유저정보 OAuth2UserRequest에 담겨있다
	 * 리턴될 때 객체가 시큐리티 세션에 저장
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("유저정보"+super.loadUser(userRequest).getAttribute("response"));
		return super.loadUser(userRequest);
	}
}
