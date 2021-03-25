package br.com.mercadolivre.social.questions.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.mercadolivre.product.repository.ProductRepository;
import br.com.mercadolivre.security.configuration.AuthenticatedUser;
import br.com.mercadolivre.social.questions.dto.QuestionRequest;
import br.com.mercadolivre.social.questions.repository.QuestionRepository;
import br.com.mercadolivre.social.questions.utils.EmailSender;

@RestController
@RequestMapping
public class QuestionController {

	private QuestionRepository questionRepository;
	private ProductRepository productRepository;
	private EmailSender emailSender;

	public QuestionController(QuestionRepository questionRepository,
			ProductRepository productRepository, EmailSender emailSender) {
		this.questionRepository = questionRepository;
		this.productRepository = productRepository;
		this.emailSender = emailSender;

	}

	@PostMapping("/product/question")
	public ResponseEntity<Object> create(
			@RequestBody @Valid QuestionRequest request,
			@AuthenticationPrincipal AuthenticatedUser user) {
		var question = questionRepository
				.save(request.toQuestion(productRepository, user.get()));
		emailSender.send(question.getQuestion(), question.getQuestion(),
				user.get().getLogin(), question.getProduct().getUser().getLogin());
		return ResponseEntity.ok().build();
	}

}
