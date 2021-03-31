package br.com.mercadolivre.purchase.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.mercadolivre.product.repository.ProductRepository;
import br.com.mercadolivre.purchase.dto.PurchaseRequest;
import br.com.mercadolivre.purchase.repository.PurchaseRepository;
import br.com.mercadolivre.security.configuration.AuthenticatedUser;
import br.com.mercadolivre.social.utils.EmailSender;

@RestController
@RequestMapping("/product/purchase")
public class PurchaseController {

	private final ProductRepository productRepository;
	private final PurchaseRepository repository;
	private final EmailSender emailSender;

	public PurchaseController(ProductRepository productRepository,
			PurchaseRepository repository, EmailSender emailSender) {
		this.productRepository = productRepository;
		this.repository = repository;
		this.emailSender = emailSender;

	}

	@PostMapping
	public ResponseEntity<String> postMethodName(
			@RequestBody @Valid PurchaseRequest dto,
			@AuthenticationPrincipal AuthenticatedUser user)
			throws MethodArgumentNotValidException {
		var product = productRepository.findById(dto.getProductId()).get();

		if (!product.withdrawStock(dto.getQuantity())) {
			var binder = new WebDataBinder(null);
			var field = new FieldError("", "quantity",
					"Invalid value. insufficient stock");
			binder.getBindingResult().addError(field);
			throw new MethodArgumentNotValidException(null, binder.getBindingResult());
		}

		productRepository.save(product);
		var purchase = repository.save(dto.toPurchase(product, user.get()));

		emailSender.send("person is interested in buying your product",
				"A customer is buying the product" + product.getName(),
				"noreply@email.com", product.getUser().getLogin());

		var redirectUrl = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}/{getaway}")
				.buildAndExpand(purchase.getId(),
						purchase.getGetaway().toString().toLowerCase())
				.toUriString();

		var url = purchase.getGetaway().getUrl() + "?returnId="
				+ purchase.getId() + "&redirectUrl=" + redirectUrl;

		return new ResponseEntity<>(url, HttpStatus.FOUND);
	}

}
