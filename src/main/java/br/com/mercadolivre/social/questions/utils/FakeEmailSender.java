package br.com.mercadolivre.social.questions.utils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({ "dev", "test" })
@Component
public class FakeEmailSender implements EmailSender {

	@Override
	public void send(String title, String subject, String from, String to) {
		var email = "------------------------------\n"
				+ String.format("from: %s\n", from)
				+ String.format("to: %s\n", to)
				+ "------------------------------\n"
				+ String.format("title: %s\n", title)
				+ String.format("subject: \n%s\n", subject)
				+ "------------------------------";

		System.out.println(email);

	}

}
