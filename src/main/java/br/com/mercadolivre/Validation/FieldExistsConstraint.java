package br.com.mercadolivre.Validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = FieldExistsValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldExistsConstraint {

	String field();

	Class<?> entityClass();

	String message() default "id cannot be found";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
