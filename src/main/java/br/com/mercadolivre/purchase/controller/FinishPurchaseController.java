package br.com.mercadolivre.purchase.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.mercadolivre.purchase.dto.FinishPurchaseDto;
import br.com.mercadolivre.purchase.model.Purchase;
import br.com.mercadolivre.purchase.repository.PurchaseRepository;
import br.com.mercadolivre.purchase.service.ProcessNewPurchase;
import br.com.mercadolivre.validation.FieldExistsConstraint;

@Validated
@RestController
@RequestMapping("/product/purchase")
public class FinishPurchaseController {

	PurchaseRepository purchaseRepository;
	ProcessNewPurchase process;

	public FinishPurchaseController(PurchaseRepository purchaseRepository,
			ProcessNewPurchase process) {
		this.purchaseRepository = purchaseRepository;
		this.process = process;
	}

	@PostMapping(value = "/{purchaseId}")
	public ResponseEntity<String> responseGetaway(
			@PathVariable @FieldExistsConstraint(entityClass = Purchase.class,
					field = "id") Long purchaseId,
			@Valid @RequestBody FinishPurchaseDto dto) {

		var purchase = purchaseRepository.findById(purchaseId).get();

		if (purchase.purchaseIsFinished())
			return ResponseEntity.badRequest().build();

		purchase.setFinishPurchase(dto);
		purchaseRepository.save(purchase);

		process.process(purchase);

		return ResponseEntity.ok(dto.getStatus().toString());
	}

}
