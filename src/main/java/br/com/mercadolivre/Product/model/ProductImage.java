package br.com.mercadolivre.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.URL;

@Entity
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@URL
	@Column(nullable = false)
	private String uri;
	@ManyToOne
	private Product product;

	public ProductImage(String uri, Product product) {
		this.uri = uri;
		this.product = product;

	}

	@Deprecated
	public ProductImage() {

	}

	public Long getId() {
		return this.id;
	}

	public String getUri() {
		return this.uri;
	}

	public Product getProduct() {
		return this.product;
	}

}
