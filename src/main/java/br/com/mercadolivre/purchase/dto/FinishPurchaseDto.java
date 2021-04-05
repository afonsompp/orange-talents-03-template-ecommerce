package br.com.mercadolivre.purchase.dto;

import javax.validation.constraints.NotNull;
import br.com.mercadolivre.purchase.model.FinishPurchase;
import br.com.mercadolivre.purchase.model.Purchase;
import br.com.mercadolivre.purchase.model.enums.ReturnPayment;
import br.com.mercadolivre.validation.EnumValidConstraint;

public class FinishPurchaseDto {

	@NotNull
	private Long paymentId;
	@EnumValidConstraint
	private ReturnPayment status;

	public FinishPurchaseDto() {
	}

	public FinishPurchaseDto(Long paymentId, ReturnPayment status) {
		this.paymentId = paymentId;
		this.status = status;
	}

	public Long getPaymentId() {
		return this.paymentId;
	}

	public ReturnPayment getStatus() {
		return this.status;
	}

	public FinishPurchase toFinishPurchase(Purchase purchase) {
		return new FinishPurchase(paymentId, status, purchase);
	}

}
