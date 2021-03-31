package br.com.mercadolivre.purchase.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.purchase.model.Purchase;
import br.com.mercadolivre.purchase.model.enums.PaymentMethod;
import br.com.mercadolivre.user.model.User;
import br.com.mercadolivre.validation.EnumValidConstraint;
import br.com.mercadolivre.validation.FieldExistsConstraint;

public class PurchaseRequest {

	@NotNull
	@FieldExistsConstraint(entityClass = Product.class, field = "id")
	private Long productId;
	@NotNull
	@Positive
	private Integer quantity;
	@EnumValidConstraint(message = "{enum.gataway}")
	private PaymentMethod getaway;

	public PurchaseRequest() {
	}

	public PurchaseRequest(Long productId, Integer quantity, PaymentMethod getaway) {
		this.productId = productId;
		this.quantity = quantity;
		this.getaway = getaway;
	}

	public Long getProductId() {
		return this.productId;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public PaymentMethod getGetaway() {
		return this.getaway;
	}

	public Purchase toPurchase(Product product, User user) {
		return new Purchase(product, product.getPrice(), quantity, getaway, user);
	}

}
