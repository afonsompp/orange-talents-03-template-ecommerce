package br.com.mercadolivre.product.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import br.com.mercadolivre.product.model.Category;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.product.repository.CategoryRepository;
import br.com.mercadolivre.user.model.User;
import br.com.mercadolivre.validation.FieldExistsConstraint;
import io.jsonwebtoken.lang.Assert;

public class ProductRequest {
	@NotBlank
	private String name;
	@NotNull
	@Positive
	private BigDecimal price;
	@PositiveOrZero
	@NotNull
	private Integer quantity;
	@NotBlank
	@Size(max = 1000)
	private String description;
	@NotNull
	@FieldExistsConstraint(entityClass = Category.class, field = "id")
	private Long categoryId;
	@Size(min = 3)
	@NotNull
	private List<ProductFeatureRequest> features;

	@Deprecated
	public ProductRequest() { // jackson
	}

	public String getName() {
		return this.name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public String getDescription() {
		return this.description;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public List<ProductFeatureRequest> getFeatures() {
		return this.features;
	}

	public Product toProduct(CategoryRepository repository, User user) {
		var productFeatures = features
				.stream()
				.map(ProductFeatureRequest::toProductFeature)
				.collect(Collectors.toList());

		var category = repository.findById(categoryId);
		Assert.isTrue(category.isPresent(), "[BUG] FieldExistsValidator don't working");

		return new Product(name, price, quantity, description, productFeatures,
				category.get(), user);

	}
}
