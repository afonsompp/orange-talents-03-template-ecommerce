package br.com.mercadolivre.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueFieldValidator
		implements ConstraintValidator<UniqueFieldConstraint, Object> {

	private String field;
	private Class<?> clazz;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void initialize(UniqueFieldConstraint constraintAnnotation) {
		this.field = constraintAnnotation.field();
		this.clazz = constraintAnnotation.entityClass();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Query query;
		query = entityManager.createQuery("Select " + field + " FROM "
				+ clazz.getSimpleName() + " WHERE " + field + " = :value");
		query.setParameter("value", value);

		if (query.getResultList().size() > 0) {
			return false;
		}
		return true;
	}

}
