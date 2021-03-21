package br.com.mercadolivre.Product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.mercadolivre.Product.dto.CategoryRequest;
import br.com.mercadolivre.Product.dto.CategoryResponse;
import br.com.mercadolivre.Product.repository.CategoryRepository;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/category")
public class CategoryController {

	private final CategoryRepository categoryRepository;

	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;

	}

	@PostMapping
	public ResponseEntity<CategoryResponse> save(
			@RequestBody @Valid CategoryRequest request) {

		var category = request.parseToCategory(categoryRepository);
		category = categoryRepository.save(category);

		return ResponseEntity.ok(new CategoryResponse(category));
	}

}
