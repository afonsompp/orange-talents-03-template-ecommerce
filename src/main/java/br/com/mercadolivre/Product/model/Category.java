package br.com.mercadolivre.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String name;
	@ManyToOne
	private Category category;

	@Deprecated
	public Category() {

	}

	public Category(String name, Category category) {
		this.name = name;
		this.category = category;
	}

	public Category(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Category)) {
			return false;
		}
		Category category = (Category) o;
		return id.equals(category.id) && name.equals(category.name);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
