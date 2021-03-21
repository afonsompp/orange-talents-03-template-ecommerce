package br.com.mercadolivre.User.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import br.com.mercadolivre.User.model.User;
import br.com.mercadolivre.Validation.UniqueFieldConstraint;

public class UserRequest {
	@NotBlank
	@Email
	@UniqueFieldConstraint(entityClass = User.class, field = "login")
	private String login;
	@NotBlank
	@Size(min = 6)
	private String password;

	public UserRequest(String login, String password) {
		this.login = login;
		this.password = password;
	}

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
