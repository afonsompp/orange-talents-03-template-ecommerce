package br.com.mercadolivre.product.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.social.questions.dto.QuestionsResponse;
import br.com.mercadolivre.social.reviews.dto.ReviewResponse;

public class ProductDetailResponse {
	private String name;
	private BigDecimal price;
	private Integer quantity;
	private String description;
	private CategoryResponse category;
	@JsonIgnoreProperties("product")
	private List<ProductFeatureResponse> features;
	@JsonIgnoreProperties("product")
	private List<ProductImageResponse> images;
	@JsonIgnoreProperties("product")
	private List<QuestionsResponse> questions;
	@JsonIgnoreProperties("product")
	private List<ReviewResponse> reviews;
	private Double reviewAverage;
	private Integer numberOfReview;

	public ProductDetailResponse(Product product) {
		this.name = product.getName();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
		this.description = product.getDescription();
		this.category = new CategoryResponse(product.getCategory());
		this.features = product.getFeatures().stream().map(ProductFeatureResponse::new)
				.collect(Collectors.toList());

		this.images = product.getImages().stream().map(ProductImageResponse::new)
				.collect(Collectors.toList());

		this.reviews = product.getReviews().stream().map(ReviewResponse::new)
				.collect(Collectors.toList());

		this.questions = product.getQuesions().stream().map(QuestionsResponse::new)
				.collect(Collectors.toList());

		this.numberOfReview = product.getNumberOfReviews();
		this.reviewAverage = BigDecimal.valueOf(product.getReviewsRatingAvg())
				.setScale(2, RoundingMode.HALF_UP).doubleValue();

	}

	@Deprecated
	public ProductDetailResponse() { // jackson
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

	public List<ProductImageResponse> getImages() {
		return this.images;
	}

	public List<ReviewResponse> getReviews() {
		return this.reviews;
	}

	public List<QuestionsResponse> getQuestions() {
		return this.questions;
	}

	public Integer getNumberOfReview() {
		return numberOfReview;
	}

	public Double getReviewAverage() {
		return reviewAverage;
	}

}
