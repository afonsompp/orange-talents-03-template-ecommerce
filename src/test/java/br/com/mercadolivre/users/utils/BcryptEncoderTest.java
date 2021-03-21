package br.com.mercadolivre.users.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import br.com.mercadolivre.user.utils.BcryptEncoder;

@SpringBootTest
public class BcryptEncoderTest {

	private BCryptPasswordEncoder encoder;
	private String password = "password";

	@BeforeEach
	public void setUp() {
		this.encoder = new BCryptPasswordEncoder();
	}

	@Test
	@DisplayName("should return password if it is already hashed")
	public void test1() {
		var hashedPass = encoder.encode(password);

		var result = BcryptEncoder.encodeIfPlainText(hashedPass);

		assertEquals(hashedPass, result);
	}

	@Test
	@DisplayName("should return hash of password if his be plain text")
	public void test2() {
		var result = BcryptEncoder.encodeIfPlainText(password);

		assertNotEquals(password, result);
	}
}
