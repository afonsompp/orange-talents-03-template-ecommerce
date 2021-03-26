package br.com.mercadolivre.social.questions.dto;

import br.com.mercadolivre.social.questions.model.Question;

public class QuestionsResponse {
	private Long id;
	private String question;

	@Deprecated
	public QuestionsResponse() { // jackson

	}

	public QuestionsResponse(Question question) {
		this.id = question.getId();
		this.question = question.getQuestion();
	}

	public Long getId() {
		return id;
	}

	public String getQuestion() {
		return question;
	}

}
