package br.com.mercadolivre.product.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
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
	private List<ProductFeature> features;

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
			List<ProductFeature> features, Category category, User user) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
		this.features = features;
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

}
