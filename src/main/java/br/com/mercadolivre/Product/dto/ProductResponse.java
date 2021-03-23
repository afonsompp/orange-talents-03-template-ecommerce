package br.com.mercadolivre.product.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import br.com.mercadolivre.product.model.Product;

public class ProductResponse {
	private String name;
	private BigDecimal price;
	private Integer quantity;
	private String description;
	private CategoryResponse category;
	private List<ProductFeatureResponse> features;

	@Deprecated
	public ProductResponse() { // jackson

	}

	public ProductResponse(Product product) {
		this.name = product.getName();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
		this.description = product.getDescription();
		this.category = new CategoryResponse(product.getCategory());
		this.features = product.getFeatures()
				.stream()
				.map(ProductFeatureResponse::new)
				.collect(Collectors.toList());
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

	public CategoryResponse getCategory() {
		return this.category;
	}

	public List<ProductFeatureResponse> getFeatures() {
		return this.features;
	}

}
