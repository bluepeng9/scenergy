package com.wbm.scenergyspring.config.auth.userinfo;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes;
	public NaverUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getGender() {
		return (String)attributes.get("gender");
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}
}
