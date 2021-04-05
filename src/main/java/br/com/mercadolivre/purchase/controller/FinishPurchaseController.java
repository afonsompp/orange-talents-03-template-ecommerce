package br.com.mercadolivre.purchase.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.mercadolivre.purchase.dto.FinishPurchaseDto;
import br.com.mercadolivre.purchase.model.Purchase;
import br.com.mercadolivre.purchase.model.enums.ReturnPayment;
import br.com.mercadolivre.purchase.repository.PurchaseRepository;
import br.com.mercadolivre.social.utils.EmailSender;
import br.com.mercadolivre.validation.FieldExistsConstraint;

@Validated
@RestController
@RequestMapping("/product/purchase")
public class FinishPurchaseController {

	PurchaseRepository purchaseRepository;
	private EmailSender emailSender;

	public FinishPurchaseController(PurchaseRepository purchaseRepository,
			EmailSender emailSender) {
		this.purchaseRepository = purchaseRepository;
		this.emailSender = emailSender;
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

		if (ReturnPayment.SUCCESS.equals(dto.getStatus())) {
			sendToInvoiceSystem(purchaseId, purchase.getUser().getId());
			sendToRanking(purchaseId, purchase.getProduct().getUser().getId());
			sendSuccessEmail(purchase);

		} else if (ReturnPayment.ERROR.equals(dto.getStatus())) {
			sendFailEmail(purchase);
		}

		return ResponseEntity.ok(dto.getStatus().toString());
	}

	private void sendFailEmail(Purchase purchase) {
		var url = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/product/purchase").toUriString();

		emailSender.send("Falha ao realizar o pagamento",
				"A comprado do produto " + purchase.getProduct().getName()
						+ ". Não foi realizada com sucesso, utilize esse link: " + url
						+ " para realizar a compra novamente",
				"no-reply@email.com", purchase.getProduct().getUser().getLogin());
	}

	private void sendSuccessEmail(Purchase purchase) {
		emailSender.send("Compra foi concluída com sucesso",
				"A compra do produto: " + purchase.getProduct().getName()
						+ "\nNa quantidade de " + purchase.getQuantity()
						+ "\nCom o valor de " + purchase.getPrice()
						+ "\nFoi realizada com sucesso!",
				"no-reply@email.com", purchase.getUser().getLogin());
	}

	private void sendToInvoiceSystem(Long purchaseId, Long customerId) {
		var url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/invoice")
				.toUriString();
		RestTemplate rt = new RestTemplate();
		rt.postForEntity(url, null, null, purchaseId, customerId);

	}

	private void sendToRanking(Long purchaseId, Long sellerId) {
		var url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/ranking")
				.toUriString();
		RestTemplate rt = new RestTemplate();
		rt.postForEntity(url, null, null, purchaseId, sellerId);

	}

}
