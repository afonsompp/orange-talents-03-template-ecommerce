package br.com.mercadolivre.product.dto;

import br.com.mercadolivre.product.model.Category;

public class CategoryResponse {
	private Long id;
	private String name;
	private Category category;

	public CategoryResponse(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.category = category.getCategory();
	}

	@Deprecated
	public CategoryResponse() {

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

}
