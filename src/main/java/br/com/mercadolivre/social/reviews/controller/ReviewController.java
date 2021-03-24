package br.com.mercadolivre.social.reviews.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.mercadolivre.product.repository.ProductRepository;
import br.com.mercadolivre.security.configuration.AuthenticatedUser;
import br.com.mercadolivre.social.reviews.dto.ReviewRequest;
import br.com.mercadolivre.social.reviews.dto.ReviewResponse;
import br.com.mercadolivre.social.reviews.repository.ReviewRepository;

@RestController
@RequestMapping
public class ReviewController {

	private ReviewRepository reviewRepository;
	private ProductRepository productRepository;

	public ReviewController(ReviewRepository reviewRepository,
			ProductRepository productRepository) {
		this.reviewRepository = reviewRepository;
		this.productRepository = productRepository;

	}

	@PostMapping("product/review")
	public ResponseEntity<ReviewResponse> save(
			@RequestBody @Valid ReviewRequest request,
			@AuthenticationPrincipal AuthenticatedUser user) {
		var review = request.toReview(productRepository, user.get());
		var savedReview = reviewRepository.save(review);
		return ResponseEntity.ok(new ReviewResponse(savedReview));
	}

}
