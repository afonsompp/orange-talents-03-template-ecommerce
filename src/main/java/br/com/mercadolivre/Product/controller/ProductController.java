package br.com.mercadolivre.product.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

	@GetMapping
	public List<ProductResponse> showAll() {

		var product = productRepository.findAll();

		return product.stream().map(ProductResponse::new).collect(Collectors.toList());
	}

}
