package br.com.mercadolivre.Error;

public class FieldErrors {

	private String field;
	private String message;

	public FieldErrors(String field, String message) {
		this.field = field;
		this.message = message;

	}

	public String getField() {
		return this.field;
	}

	public String getMessage() {
		return this.message;
	}
}
