package br.com.mercadolivre.security.configuration;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UsersService implements UserDetailsService {

	private EntityManager manager;
	@Value("${security.username-query}")
	private String query;
	private UserDetailsMapper userDetailsMapper;

	public UsersService(EntityManager manager, UserDetailsMapper userDetailsMapper) {
		this.manager = manager;
		this.userDetailsMapper = userDetailsMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		List<?> objects = manager.createQuery(query)
				.setParameter("username", username).getResultList();
		Assert.isTrue(objects.size() <= 1,
				"[BUG] more than one authenticable user. " + username);

		if (objects.isEmpty()) {
			throw new UsernameNotFoundException(
					"User with login: " + username + " Not found");
		}

		return userDetailsMapper.map(objects.get(0));
	}

}
