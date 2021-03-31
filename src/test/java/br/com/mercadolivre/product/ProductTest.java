package br.com.mercadolivre.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.social.reviews.model.Review;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@PropertySource("classpath:application.properties")
class ProductTest {

	@DisplayName("Should return correct number and avg rating of reviews")
	@ParameterizedTest
	@MethodSource("provideReviews")
	void test1(List<Review> reviews, Integer count, Double avg) {

		var product = new Product("", new BigDecimal(5), 5, "", null, null, null, reviews,
				null, null);

		assertEquals(count, product.getNumberOfReviews());
		assertEquals(avg, product.getReviewsRatingAvg());
	}

	private static Stream<Arguments> provideReviews() {
		var rate1 = Math.random() * 5;
		var rate3 = Math.random() * 5;
		var rate2 = Math.random() * 5;
		var avg = (rate1 + rate2 + rate3) / 3;

		var reviews = Arrays.asList(
				new Review(rate1, "title", "description", null, null),
				new Review(rate2, "title", "description", null, null),
				new Review(rate3, "title", "description", null, null));
		var count = reviews.size();

		return Stream.of(
				Arguments.of(null, 0, 0.0),
				Arguments.of(new ArrayList<>(), 0, 0.0),
				Arguments.of(reviews, count, avg));
	}

	@DisplayName("Should withdraw stock when her is greater than 0 and quantity is less than stock")
	@ParameterizedTest
	@MethodSource("provideStock")
	public void test2(Integer stock, Integer quantity, Boolean result) {
		var product =
				new Product("", null, stock, "", null, null, null, null, null, null);

		assertEquals(result, product.withdrawStock(quantity));
		if (result) {
			assertEquals(result, product.getQuantity() == (stock - quantity));
		}
	}

	private static Stream<Arguments> provideStock() {
		return Stream.of(
				Arguments.of(null, 1, false),
				Arguments.of(1, null, false),
				Arguments.of(0, 1, false),
				Arguments.of(1, 2, false),
				Arguments.of(1, 1, true));
	}

}
