package br.com.mercadolivre.social.questions.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.util.Assert;
import br.com.mercadolivre.product.model.Product;
import br.com.mercadolivre.product.repository.ProductRepository;
import br.com.mercadolivre.social.questions.model.Question;
import br.com.mercadolivre.user.model.User;
import br.com.mercadolivre.validation.FieldExistsConstraint;

public class QuestionRequest {
	@NotBlank
	private String question;
	@NotNull
	@FieldExistsConstraint(entityClass = Product.class, field = "id")
	private Long productId;

	@Deprecated
	public QuestionRequest() {

	}

	public QuestionRequest(String question, Long productId) {
		this.question = question;
		this.productId = productId;
	}

	public String getQuestion() {
		return this.question;
	}

	public Long getProductId() {
		return this.productId;
	}

	public Question toQuestion(ProductRepository productRepository, User user) {
		var product = productRepository.findById(productId);
		Assert.isTrue(product.isPresent(), "[BUG] FieldExistsValidator don't working");
		return new Question(question, product.get(), user);
	}

}
