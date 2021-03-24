package br.com.mercadolivre.product.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import br.com.mercadolivre.product.dto.ProductImageRequest;
import br.com.mercadolivre.product.dto.ProductImageResponse;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.product.model.ProductImage;
import br.com.mercadolivre.product.repository.ProductRepository;
import br.com.mercadolivre.product.utils.CloudSender;
import br.com.mercadolivre.security.configuration.AuthenticatedUser;

@RestController
@RequestMapping
public class ProductImageController {

	// 1
	private ProductRepository productRepository;
	private CloudSender<ProductImage, Product> cSender;

	@Value("${product.image}")
	private String host;

	public ProductImageController(ProductRepository productRepository,
			CloudSender<ProductImage, Product> cSender) {
		this.productRepository = productRepository;
		this.cSender = cSender;
	}

	@PostMapping(value = "product/{id}/images")
	public ResponseEntity<List<ProductImageResponse>> savePicture(@PathVariable Long id,
			@Valid ProductImageRequest images,
			@AuthenticationPrincipal AuthenticatedUser user) {

		var optionalProduct = productRepository.findById(id);
		if (optionalProduct.isEmpty())
			return ResponseEntity.badRequest().build();

		var product = optionalProduct.get();
		var productImages = cSender.send(product, images.getImages(), host);

		if (!product.isOwnership(user.get()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);

		product.addImages(productImages);
		var savedProducts = productRepository.save(product);
		return ResponseEntity
				.ok(ProductImageResponse.toResponse(savedProducts.getImages()));

	}
}
