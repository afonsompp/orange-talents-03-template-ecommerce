package br.com.mercadolivre.product.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;
import br.com.mercadolivre.product.dto.ProductFeatureRequest;
import br.com.mercadolivre.social.questions.model.Question;
import br.com.mercadolivre.social.reviews.model.Review;
import br.com.mercadolivre.user.model.User;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false, length = 1000)
	private String description;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductFeature> features = new ArrayList<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductImage> images = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<Question> questions = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<Review> reviews = new ArrayList<>();

	@ManyToOne
	@JoinColumn(nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant createAt;

	@Deprecated
	public Product() { // hibernate
	}

	public Product(String name, BigDecimal price, Integer quantity, String description,
			List<ProductFeatureRequest> features, Category category, User user) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.category = category;
		this.user = user;
		this.features.addAll(features.stream().map(f -> f.toProductFeature(this))
				.collect(Collectors.toList()));
	}

	public Product(String name, BigDecimal price, Integer quantity, String description,
			List<ProductFeature> features, List<ProductImage> images,
			List<Question> questions, List<Review> reviews, Category category,
			User user) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.features = features;
		this.images = images;
		this.questions = questions;
		this.reviews = reviews;
		this.category = category;
		this.user = user;
	}

	public Long getId() {
		return this.id;
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

	public List<ProductFeature> getFeatures() {
		return this.features;
	}

	public Category getCategory() {
		return this.category;
	}

	public Instant getCreateAt() {
		return this.createAt;
	}

	public User getUser() {
		return user;
	}

	public List<ProductImage> getImages() {
		return images;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public Integer getNumberOfReviews() {
		if (reviews == null || reviews.isEmpty()) {
			return 0;
		}
		return reviews.size();
	}

	public Double getReviewsRatingAvg() {
		if (reviews == null || reviews.isEmpty()) {
			return Double.valueOf(0);
		}

		var rateSum = reviews.stream().map(Review::getRate)
				.collect(Collectors.summarizingDouble(Double::doubleValue)).getSum();
		return rateSum / reviews.size();
	}

	public void addImages(List<ProductImage> images) {
		Assert.notEmpty(images, "[BUG] Collection of images cannot be null");
		Assert.noNullElements(images, "[BUG] collection of images cannot be null");
		this.images.addAll(images);
	}

	public boolean withdrawStock(Integer quantity) {
		if (this.quantity == null || quantity == null || this.quantity.intValue() <= 0
				|| this.quantity < quantity)
			return false;

		this.quantity -= quantity;
		return true;

	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Product)) {
			return false;
		}
		Product product = (Product) o;
		return id.equals(product.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public boolean isOwnership(User user) {
		return this.user.getId().longValue() == user.getId().longValue();
	}

}
