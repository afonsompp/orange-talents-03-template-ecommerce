package br.com.mercadolivre.purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import br.com.mercadolivre.purchase.model.enums.ReturnPayment;

public class PaymentStatusTest {
	@DisplayName("should return enum option if exists or null if not exists")
	@ParameterizedTest
	@MethodSource("provideOptions")
	public void test1(Object value, ReturnPayment result) {
		var getaway = ReturnPayment.fromObject(value);

		assertEquals(result, getaway);
	}

	private static Stream<Arguments> provideOptions() {
		return Stream.of(
				Arguments.of("SUCESSO", ReturnPayment.SUCCESS),
				Arguments.of(1, ReturnPayment.SUCCESS),
				Arguments.of("SUCESS", null),
				Arguments.of("ERRO", ReturnPayment.ERROR),
				Arguments.of("0", ReturnPayment.ERROR),
				Arguments.of("ERR", null));
	}
}
