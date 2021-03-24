package br.com.mercadolivre.product.dto;

import java.util.List;
import java.util.stream.Collectors;
import br.com.mercadolivre.product.model.ProductImage;

public class ProductImageResponse {

	private Long id;
	private String uri;

	@Deprecated
	public ProductImageResponse() {

	}

	public ProductImageResponse(ProductImage productImage) {

		this.id = productImage.getId();
		this.uri = productImage.getUri();

	}

	public Long getId() {
		return this.id;
	}

	public String getUri() {
		return this.uri;
	}

	public static List<ProductImageResponse> toResponse(List<ProductImage> images) {
		return images.stream().map(ProductImageResponse::new)
				.collect(Collectors.toList());
	}

}
