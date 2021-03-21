package br.com.mercadolivre.user.utils;

import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoder {
	private static Pattern BCRYPT_PATTERN =
			Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

	public static String encodeIfPlainText(String password) {
		var matcher = BCRYPT_PATTERN.matcher(password);
		if (matcher.matches()) {
			return password;
		}
		var encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
}
