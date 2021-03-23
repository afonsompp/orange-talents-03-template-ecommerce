package br.com.mercadolivre.product.dto;

import br.com.mercadolivre.product.model.ProductFeature;

public class ProductFeatureResponse {
	private String feature;
	private String description;

	@Deprecated
	public ProductFeatureResponse() { // jackson

	}

	public ProductFeatureResponse(ProductFeature productFeature) {
		this.feature = productFeature.getFeature();
		this.description = productFeature.getDescription();
	}

	public String getFeature() {
		return feature;
	}

	public String getDescription() {
		return description;
	}

}
