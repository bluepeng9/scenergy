package com.wbm.scenergyspring.config.oauth;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.wbm.scenergyspring.config.auth.PrincipalDetails;
import com.wbm.scenergyspring.config.auth.userinfo.NaverUserInfo;
import com.wbm.scenergyspring.config.auth.userinfo.OAuth2UserInfo;
import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	/**
	 *
	 * Access Token 얻고나서 실행
	 * 토큰과 유저정보 OAuth2UserRequest에 담겨있다
	 * 리턴될 때 객체가 시큐리티 세션에 저장
	 *
	 */

	final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		if (registrationId.equals("naver")) {
			return handleNaverLogin(oAuth2User);
		}
		return super.loadUser(userRequest);
	}

	private PrincipalDetails handleNaverLogin(OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));

		String email = oAuth2UserInfo.getEmail();
		Gender gender = oAuth2UserInfo.getGender().equals("F") ? Gender.FEMALE : Gender.MALE;

		// 해당아이디로 회원가입 되어있는지 확인
		Optional<User> user = userRepository.findByEmail(email);

		// 이미 회원가입이 되어있으면 그냥 로그인
		if (user.isPresent()) {
			User userEntity = user.get();

			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		}

		String username = oAuth2UserInfo.getName();
		User newUser = User.createNewUser(email,
			null,
			username,
			gender,
			null
		);
		userRepository.save(newUser);

		return new PrincipalDetails(newUser, oAuth2User.getAttributes());
	}

}
