package br.com.mercadolivre.social.reviews.dto;

import br.com.mercadolivre.product.dto.ProductResponse;
import br.com.mercadolivre.social.reviews.model.Review;

public class ReviewResponse {
	private Long id;
	private Double rate;
	private String title;
	private String description;
	private ProductResponse product;

	public ReviewResponse(Review review) {
		this.id = review.getId();
		this.rate = review.getRate();
		this.title = review.getTitle();
		this.description = review.getDescription();
		this.product = new ProductResponse(review.getProduct());
	}

	@Deprecated
	public ReviewResponse() {

	}

	public Long getId() {
		return this.id;
	}

	public Double getRate() {
		return this.rate;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public ProductResponse getProduct() {
		return this.product;
	}

}
