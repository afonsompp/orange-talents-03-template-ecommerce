package br.com.mercadolivre.User.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import br.com.mercadolivre.User.utils.BcryptEncoder;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String login;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Instant instant;

	@Deprecated
	public User() {

	}

	public User(@Email @NotBlank String login,
			@NotBlank @Size(min = 6) String password) {
		this.login = login;
		this.password = BcryptEncoder.encodeIfPlainText(password);
	}

	public Long getId() {
		return this.id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return this.password;
	}

	public Instant getInstant() {
		return this.instant;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return this.id.equals(user.getId());
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
