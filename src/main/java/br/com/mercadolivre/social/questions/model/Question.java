package br.com.mercadolivre.social.questions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.user.model.User;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String question;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Product product;
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@Deprecated
	public Question() {

	}

	public Question(String question, Product product, User user) {
		this.question = question;
		this.product = product;
		this.user = user;
	}

	public Long getId() {
		return this.id;
	}

	public String getQuestion() {
		return this.question;
	}

	public Product getProduct() {
		return this.product;
	}

	public User getUser() {
		return this.user;
	}

}
