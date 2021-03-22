package br.com.mercadolivre.product.dto;

import javax.validation.constraints.NotBlank;
import org.springframework.util.Assert;
import br.com.mercadolivre.product.model.Category;
import br.com.mercadolivre.product.repository.CategoryRepository;
import br.com.mercadolivre.validation.FieldExistsConstraint;
import br.com.mercadolivre.validation.UniqueFieldConstraint;

public class CategoryRequest {

	@NotBlank
	@UniqueFieldConstraint(entityClass = Category.class, field = "name")
	private String name;
	@FieldExistsConstraint(entityClass = Category.class, field = "id")
	private Long categoryId;

	@Deprecated
	public CategoryRequest() {

	}

	public CategoryRequest(String name, Long categoryId) {
		this.name = name;
		this.categoryId = categoryId;
	}

	public String getName() {
		return this.name;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public Category parseToCategory(CategoryRepository repository) {
		if (categoryId == null) {
			return new Category(name);
		}

		var category = repository.findById(categoryId);
		Assert.isTrue(category.isPresent(), "[BUG] FieldExistsValidator don't working");
		return new Category(name, category.get());
	}
}
