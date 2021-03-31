package br.com.mercadolivre.purchase.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentMethod {

	PAYPAL("https://www.paypal.com"),
	PAGSEGURO("https://www.pagseguro.com.br");

	private String url;

	PaymentMethod(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@JsonCreator
	public static PaymentMethod fromString(String value) {
		if (value == null) {
			return null;
		}
		for (PaymentMethod v : values()) {
			if (value.equalsIgnoreCase(v.toString())) {
				return v;
			}
		}
		return null;
	}
}
