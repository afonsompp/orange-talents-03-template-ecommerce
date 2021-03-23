package br.com.mercadolivre.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import br.com.mercadolivre.security.configuration.AuthenticatedUser;
import br.com.mercadolivre.security.configuration.UserDetailsMapper;
import br.com.mercadolivre.user.model.User;

@Configuration
public class UserDetailsMapperImpl implements UserDetailsMapper {

	@Override
	public UserDetails map(Object shouldBeASystemUser) {
		return new AuthenticatedUser((User) shouldBeASystemUser);
	}

}
