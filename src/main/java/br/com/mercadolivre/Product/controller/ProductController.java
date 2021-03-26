package br.com.mercadolivre.product.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.mercadolivre.product.dto.ProductDetailResponse;
import br.com.mercadolivre.product.dto.ProductRequest;
import br.com.mercadolivre.product.dto.ProductResponse;
import br.com.mercadolivre.product.repository.CategoryRepository;
import br.com.mercadolivre.product.repository.ProductRepository;
import br.com.mercadolivre.security.configuration.AuthenticatedUser;

@RestController
@RequestMapping("/product")
public class ProductController {

	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;

	public ProductController(ProductRepository productRepository,
			CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;

	}

	@PostMapping
	public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductRequest dto,
			@AuthenticationPrincipal AuthenticatedUser user) {
		var product = dto.toProduct(categoryRepository, user.get());
		var savedProduct = productRepository.save(product);
		return ResponseEntity.ok(new ProductResponse(savedProduct));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDetailResponse> getMethodName(@PathVariable Long id) {
		var product = productRepository.findById(id);

		if (product.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new ProductDetailResponse(product.get()));
	}

}
