package br.com.mercadolivre.purchase.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.util.Assert;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.purchase.dto.FinishPurchaseDto;
import br.com.mercadolivre.purchase.model.enums.PaymentMethod;
import br.com.mercadolivre.purchase.model.enums.PurchaseStatus;
import br.com.mercadolivre.purchase.model.enums.ReturnPayment;
import br.com.mercadolivre.user.model.User;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private BigDecimal price;
	@Column(nullable = false)
	private Integer quantity;
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private PaymentMethod getaway;
	@Enumerated(EnumType.ORDINAL)
	private PurchaseStatus status = PurchaseStatus.INITIATED;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Product product;
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
	Set<FinishPurchase> attemptsToFinishPurchase = new HashSet<>();

	@Deprecated
	public Purchase() {

	}

	public Purchase(Product product, BigDecimal price, Integer quantity,
			PaymentMethod getaway, User user) {
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.getaway = getaway;
		this.user = user;

	}

	public Long getId() {
		return this.id;
	}

	public Product getProduct() {
		return this.product;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public PaymentMethod getGetaway() {
		return this.getaway;
	}

	public User getUser() {
		return this.user;
	}

	public PurchaseStatus getStatus() {
		return status;
	}

	public void setFinishPurchase(FinishPurchaseDto dto) {
		Assert.isTrue(!purchaseIsFinished(), "This purchase already finished");
		this.attemptsToFinishPurchase.add(dto.toFinishPurchase(this));
	}

	private Set<FinishPurchase> getFinishPurchaseWithSuccess() {
		return this.attemptsToFinishPurchase.stream()
				.filter(f -> ReturnPayment.SUCCESS.equals(f.getStatus()))
				.collect(Collectors.toSet());
	}

	public Boolean purchaseIsFinished() {
		return !getFinishPurchaseWithSuccess().isEmpty();
	}

}
