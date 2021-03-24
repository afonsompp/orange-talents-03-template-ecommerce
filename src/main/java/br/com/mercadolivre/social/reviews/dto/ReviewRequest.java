package br.com.mercadolivre.social.reviews.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.Assert;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.product.repository.ProductRepository;
import br.com.mercadolivre.user.model.User;
import br.com.mercadolivre.validation.FieldExistsConstraint;

public class ReviewRequest {

	@NotBlank
	private String title;
	@Range(min = 1, max = 5)
	@NotNull
	private Double rate;
	@NotBlank
	@Length(max = 500)
	private String description;
	@NotNull
	@FieldExistsConstraint(field = "id", entityClass = Product.class)
	private Long productId;

	@Deprecated
	public ReviewRequest() {

	}

	public ReviewRequest(String title, Double rate, String description, Long productId) {
		this.title = title;
		this.rate = rate;
		this.description = description;
		this.productId = productId;
	}

	public String getTitle() {
		return this.title;
	}

	public Double getRate() {
		return this.rate;
	}

	public String getDescription() {
		return this.description;
	}

	public Long getProductId() {
		return this.productId;
	}

	public Review toReview(ProductRepository repository, User user) {
		var product = repository.findById(productId);

		Assert.isTrue(product.isPresent(), "[BUG] FieldExistsValidator don't working");
		return new Review(rate, title, description, product.get(), user);
	}

}
