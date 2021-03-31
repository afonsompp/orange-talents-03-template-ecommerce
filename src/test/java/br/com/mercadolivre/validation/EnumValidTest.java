package br.com.mercadolivre.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import br.com.mercadolivre.purchase.dto.PurchaseRequest;
import br.com.mercadolivre.purchase.model.enums.PaymentMethod;

@SpringBootTest
public class EnumValidTest {
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

	@DisplayName("Should verify if value passed to enum is valid")
	@ParameterizedTest
	@MethodSource("provideGetaways")
	public void test1(PurchaseRequest object, Boolean result) {

		var violations = validator.validate(object);
		var possibleError = violations.stream()
				.filter(e -> e.getPropertyPath().toString().equalsIgnoreCase("getaway"))
				.findFirst();

		assertEquals(result, possibleError.isEmpty());

	}

	private static Stream<Arguments> provideGetaways() {
		return Stream.of(
				Arguments.of(new PurchaseRequest(1L, 1, null), false),
				Arguments.of(new PurchaseRequest(1L, 1,
						PaymentMethod.fromString("PAGSEGURO")), true),
				Arguments.of(new PurchaseRequest(1L, 1,
						PaymentMethod.fromString("PAGSEGUR")), false));
	}

}
