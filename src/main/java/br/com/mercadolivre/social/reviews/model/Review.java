package br.com.mercadolivre.social.reviews.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.user.model.User;

@Entity
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Double rate;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false, length = 500)
	private String description;
	@JoinColumn(nullable = false)
	@ManyToOne
	private Product product;
	@JoinColumn(nullable = false)
	@ManyToOne
	private User user;

	@Deprecated
	public Review() { // hibernate

	}

	public Review(Double rate, String title, String description, Product product,
			User user) {
		this.rate = rate;
		this.title = title;
		this.description = description;
		this.product = product;
		this.user = user;
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

	public Product getProduct() {
		return this.product;
	}

	public User getUser() {
		return this.user;
	}

}
