package com.wbm.scenergyspring.util;

import java.util.Random;

import com.wbm.scenergyspring.domain.user.entity.User;

public class UserGenerator {
	private static int userCount = 1;

	public static User createRandomMember() {
		String email = "asdf" + userCount + "@test.com";
		User user = User.createNewUser(
			email,
			"password" + userCount,
			"nickname" + userCount
		);
		userCount += 1;
		return user;
	}

	private static String getRandomGender() {
		Random random = new Random();

		String s = random.nextInt() > 0 ? "M" : "F";

		return UserGenerator.getRandomGender();
	}
}
