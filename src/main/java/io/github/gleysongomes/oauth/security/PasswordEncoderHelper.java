package io.github.gleysongomes.oauth.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordEncoderHelper {

	public static void main(String[] args) {
		System.out.println(encod("a"));
	}

	private static String encod(String value) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode(value);
		return result;
	}

}
