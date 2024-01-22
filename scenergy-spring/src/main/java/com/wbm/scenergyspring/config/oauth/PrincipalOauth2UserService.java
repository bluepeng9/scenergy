package com.wbm.scenergyspring.config.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.wbm.scenergyspring.config.auth.PrincipalDetails;
import com.wbm.scenergyspring.config.auth.userinfo.NaverUserInfo;
import com.wbm.scenergyspring.config.auth.userinfo.OAuth2UserInfo;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	/**
	 *
	 * Access Token 얻고나서 실행
	 * 토큰과 유저정보 OAuth2UserRequest에 담겨있다
	 * 리턴될 때 객체가 시큐리티 세션에 저장
	 */

	@Autowired
	UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("유저정보"+super.loadUser(userRequest).getAttribute("response"));

		OAuth2User oAuth2User = super.loadUser(userRequest);
		OAuth2UserInfo oAuth2UserInfo = null;

		if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
			String username = oAuth2User.getAttribute("name");
			System.out.println("++++oAuth2User name : +++"+ username);
			String password = "pwd";
			String gender = oAuth2User.getAttribute("gender");
			String email = oAuth2User.getAttribute("email");

			// 해당아이디로 로그인 되어있는지 확인
			User userEntity = userRepository.findByUsername(username);
			if (userEntity == null) {
				userEntity = User.builder()
					.username(oAuth2UserInfo.getName())
					.password(password)
					.email(oAuth2UserInfo.getEmail())
					.gender(oAuth2UserInfo.getGender())
					.build();
				userRepository.save(userEntity);
			}
			System.out.println("++++++++++++++++++oAuth2User              "+oAuth2User);
			System.out.println("++++++++++++++++++oAuth2User              "+oAuth2User);
			System.out.println("+++++++++++++++++++oAuth2UserInfo    "+oAuth2UserInfo.getEmail());
			System.out.println("+++++++++++++++++++oAuth2UserInfo    "+oAuth2UserInfo.getName());

			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		}
		return super.loadUser(userRequest);
	}


}
