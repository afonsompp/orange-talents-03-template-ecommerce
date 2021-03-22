package br.com.mercadolivre.security.configuration;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Interface que você pode implementar para retornar um {@link UserDetails} em função do objeto que
 * representa sua entidade de usuário
 */
public interface UserDetailsMapper {

	/**
	 * @param shouldBeASystemUser um objeto que deveria representar seu usuário logado
	 * @return
	 */
	UserDetails map(Object shouldBeASystemUser);
}
