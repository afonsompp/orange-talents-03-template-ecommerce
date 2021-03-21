package br.com.mercadolivre.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import br.com.mercadolivre.user.dto.UserRequest;
import br.com.mercadolivre.user.model.User;

@SpringBootTest
public class UniqueFieldTest {

	@PersistenceContext
	private EntityManager em;
	private LocalValidatorFactoryBean validator;
	@Autowired
	private ConfigurableApplicationContext applicationContext;

	@BeforeEach
	private void setUpEach() {
		SpringConstraintValidatorFactory springConstraintValidatorFactory =
				new SpringConstraintValidatorFactory(
						applicationContext.getAutowireCapableBeanFactory());

		validator = new LocalValidatorFactoryBean();
		validator.setConstraintValidatorFactory(springConstraintValidatorFactory);
		validator.setApplicationContext(applicationContext);
		validator.afterPropertiesSet();
	}

	@Test
	@DisplayName("Should return error in login field")
	@Transactional
	public void test1() {

		var user = new User("user@email.com", "password");
		em.persist(user);

		var request = new UserRequest("user@email.com", "password");
		var violations = validator.validate(request);
		var error = violations.iterator().next();

		assertEquals("login", error.getPropertyPath().toString());

	}

	@Test
	@DisplayName("don't should return errors")
	@Transactional
	public void test2() {

		var user = new User("user@gmail.com", "password");
		em.persist(user);

		var request = new UserRequest("user2@gmail.com", "password");
		var violations = validator.validate(request);

		assertEquals(true, violations.isEmpty());

	}
}
