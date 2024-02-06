package com.wbm.scenergyspring.util;

import java.util.Random;

public class RandomValueGenerator {

	private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static int methodCallCount = 1;

	public static String generateRandomUrl() {
		String baseUrl = "https://www.example.com/";
		String randomId = generateRandomString(10, 20);
		return baseUrl + randomId;
	}

	//generate random string value
	public static String generateRandomString(int from, int to) {
		Random random = new Random();
		int length = from + random.nextInt(to - from + 1);
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHAR_SET.length());
			sb.append(CHAR_SET.charAt(index));
		}
		return sb.toString();
	}

	public static String generateRandomEmail() {
		methodCallCount += 1;
		String[] emailArr = {"naver.com", "gmail.com", "daum.net", "nate.com", "yahoo.com"};
		return "test" + methodCallCount + "@" + emailArr[methodCallCount % 5];
	}
}
