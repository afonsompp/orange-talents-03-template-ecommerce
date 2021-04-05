package br.com.mercadolivre.purchase.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ReturnPayment {
	SUCCESS,
	ERROR;

	@JsonCreator
	public static ReturnPayment fromObject(Object value) {
		if (value == null) {
			return null;
		}
		if (value.toString().equalsIgnoreCase("SUCESSO")
				|| value.toString().equals("1")) {
			return SUCCESS;
		} else if (value.toString().equalsIgnoreCase("ERRO")
				|| value.toString().equals("0")) {
			return ERROR;
		}
		return null;
	}
}
