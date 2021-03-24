package br.com.mercadolivre.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductFeature {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String feature;
	@Column(nullable = false)
	private String description;
	@ManyToOne
	private Product product;

	@Deprecated
	public ProductFeature() { // Hibernate
	}

	public ProductFeature(String feature, String description, Product product) {
		this.feature = feature;
		this.description = description;
		this.product = product;
	}

	public String getDescription() {
		return description;
	}

	public String getFeature() {
		return feature;
	}

	public Long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ProductFeature)) {
			return false;
		}
		ProductFeature productFeatures = (ProductFeature) o;
		return (id.equals(productFeatures.id));
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
