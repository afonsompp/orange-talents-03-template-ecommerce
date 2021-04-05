package br.com.mercadolivre.purchase.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import br.com.mercadolivre.purchase.model.enums.ReturnPayment;

@Entity
public class FinishPurchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long paymentId;
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ReturnPayment status;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Purchase purchase;
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Instant instant;

	@Deprecated
	public FinishPurchase() {
	}

	public FinishPurchase(Long paymentId, ReturnPayment status, Purchase purchase) {
		this.paymentId = paymentId;
		this.status = status;
		this.purchase = purchase;
	}

	public Long getId() {
		return this.id;
	}

	public Long getPaymentId() {
		return this.paymentId;
	}

	public ReturnPayment getStatus() {
		return this.status;
	}

	public Purchase getPurchase() {
		return this.purchase;
	}

	public Instant getInstant() {
		return this.instant;
	}

}
