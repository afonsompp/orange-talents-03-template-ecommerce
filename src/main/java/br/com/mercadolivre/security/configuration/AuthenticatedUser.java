package br.com.mercadolivre.security.configuration;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.com.mercadolivre.user.model.User;

public class AuthenticatedUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private User user;
	private org.springframework.security.core.userdetails.User springUserDetails;

	public AuthenticatedUser(User user) {
		this.user = user;
		this.springUserDetails = new org.springframework.security.core.userdetails.User(
				user.getLogin(), user.getPassword(), List.of());
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return springUserDetails.getAuthorities();
	}

	public String getPassword() {
		return springUserDetails.getPassword();
	}

	public String getUsername() {
		return springUserDetails.getUsername();
	}

	public boolean isEnabled() {
		return springUserDetails.isEnabled();
	}

	public boolean isAccountNonExpired() {
		return springUserDetails.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return springUserDetails.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return springUserDetails.isCredentialsNonExpired();
	}

	public User get() {
		return this.user;
	}

}
