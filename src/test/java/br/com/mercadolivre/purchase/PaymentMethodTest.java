package br.com.mercadolivre.purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import br.com.mercadolivre.purchase.model.enums.PaymentMethod;

public class PaymentMethodTest {

	@DisplayName("should return enum option if exists or null if not exists")
	@ParameterizedTest
	@MethodSource("provideOptions")
	public void test1(String value, PaymentMethod result) {
		var getaway = PaymentMethod.fromString(value);

		assertEquals(result, getaway);
	}

	private static Stream<Arguments> provideOptions() {
		return Stream.of(
				Arguments.of("paypa", null),
				Arguments.of(null, null),
				Arguments.of("paypal", PaymentMethod.PAYPAL));
	}

}
