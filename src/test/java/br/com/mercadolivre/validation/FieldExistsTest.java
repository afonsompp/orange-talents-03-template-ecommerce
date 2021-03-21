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
import br.com.mercadolivre.Product.dto.CategoryRequest;
import br.com.mercadolivre.Product.model.Category;

@SpringBootTest
public class FieldExistsTest {
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
	@DisplayName("Should return true becuause field is null")
	public void test1() {
		CategoryRequest cat = new CategoryRequest("name", null);
		var violations = validator.validate(cat);

		assertEquals(true, violations.isEmpty());
	}

	@Test
	@DisplayName("Shoud return error in field")
	public void test2() {
		CategoryRequest categoryRequest = new CategoryRequest("name", 1L);
		var violations = validator.validate(categoryRequest);
		var error = violations.iterator().next();

		assertEquals("categoryId", error.getPropertyPath().toString());
	}

	@Test
	@DisplayName("Should return true because field exist")
	@Transactional
	public void test3() {
		var category = new Category("name", null);
		em.persist(category);

		var categoryRequest = new CategoryRequest("name2", 1L);
		var violations = validator.validate(categoryRequest);

		assertEquals(true, violations.isEmpty());
	}
}
