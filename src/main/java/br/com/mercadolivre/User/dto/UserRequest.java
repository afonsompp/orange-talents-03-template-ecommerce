package br.com.mercadolivre.User.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import br.com.mercadolivre.User.model.User;

public class UserRequest {
	@NotBlank
	@Email
	private String login;
	@NotBlank
	@Size(min = 6)
	private String password;

	@Deprecated
	public UserRequest() {

	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public User parseToUser() {
		return new User(login, new BCryptPasswordEncoder().encode(password));
	}

}
