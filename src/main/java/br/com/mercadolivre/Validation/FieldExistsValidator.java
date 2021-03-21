package br.com.mercadolivre.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldExistsValidator
		implements ConstraintValidator<FieldExistsConstraint, Object> {
	private String field;
	private Class<?> clazz;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void initialize(FieldExistsConstraint constraintAnnotation) {
		this.field = constraintAnnotation.field();
		this.clazz = constraintAnnotation.entityClass();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		Query query;
		query = entityManager.createQuery("Select t FROM "
				+ clazz.getSimpleName() + " t WHERE " + field + " = :value");
		query.setParameter("value", value);

		if (query.getResultList().isEmpty()) {
			return false;
		}

		return true;
	}
}
