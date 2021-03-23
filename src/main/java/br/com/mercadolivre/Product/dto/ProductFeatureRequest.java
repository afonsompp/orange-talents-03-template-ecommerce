package br.com.mercadolivre.product.dto;

import javax.validation.constraints.NotBlank;
import br.com.mercadolivre.product.model.ProductFeature;

public class ProductFeatureRequest {
	@NotBlank
	private String feature;
	@NotBlank
	private String description;

	@Deprecated
	public ProductFeatureRequest() { // jackson

	}

	public String getFeature() {
		return feature;
	}

	public String getDescription() {
		return description;
	}

	public ProductFeature toProductFeature() {
		return new ProductFeature(feature, description);
	}

}
