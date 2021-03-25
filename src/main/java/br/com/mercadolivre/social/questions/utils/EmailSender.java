package br.com.mercadolivre.social.questions.utils;

public interface EmailSender {
	void send(String title, String subject, String from, String to);
}
