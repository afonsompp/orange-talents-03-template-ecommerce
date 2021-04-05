package br.com.mercadolivre.purchase.service;

import br.com.mercadolivre.purchase.model.Purchase;

public interface SuccessPurchaseEvent {
	void process(Purchase purchase);
}
