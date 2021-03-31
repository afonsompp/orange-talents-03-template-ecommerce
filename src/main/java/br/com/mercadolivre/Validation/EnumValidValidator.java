package br.com.mercadolivre.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidValidator
		implements ConstraintValidator<EnumValidConstraint, Enum<?>> {
	@Override
	public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
		return value != null;

	}

}
