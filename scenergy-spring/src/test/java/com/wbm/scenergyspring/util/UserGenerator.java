package com.wbm.scenergyspring.util;

import com.wbm.scenergyspring.domain.user.entity.Gender;
import com.wbm.scenergyspring.domain.user.entity.User;

import java.util.Random;

public class UserGenerator {
	private static int userCount = 1;

	public static User createRandomMember() {
		String email = "asdf" + userCount + "@test.com";
		User user = User.createNewUser(
			email,
			"password" + userCount,
				"tester" + userCount,
				Gender.MALE,
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
