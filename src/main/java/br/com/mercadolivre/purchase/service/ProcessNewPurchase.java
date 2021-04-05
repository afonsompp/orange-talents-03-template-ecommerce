package br.com.mercadolivre.purchase.service;

import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.mercadolivre.purchase.model.Purchase;
import br.com.mercadolivre.social.utils.EmailSender;

@Service
public class ProcessNewPurchase {

	private Set<SuccessPurchaseEvent> events;
	private EmailSender emailSender;

	public ProcessNewPurchase(Set<SuccessPurchaseEvent> events, EmailSender emailSender) {
		this.events = events;
		this.emailSender = emailSender;
	}

	public void process(Purchase purchase) {
		if (purchase.purchaseIsFinished()) {
			events.forEach(ev -> ev.process(purchase));
			emailSender.send("Compra foi concluída com sucesso",
					"A compra do produto: " + purchase.getProduct().getName()
							+ "\nNa quantidade de " + purchase.getQuantity()
							+ "\nCom o valor de " + purchase.getPrice()
							+ "\nFoi realizada com sucesso!",
					"no-reply@email.com", purchase.getUser().getLogin());
		} else {
			var url = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/product/purchase").toUriString();
			emailSender.send("Falha ao realizar o pagamento",
					"A comprado do produto " + purchase.getProduct().getName()
							+ ". Não foi realizada com sucesso, utilize esse link: " + url
							+ " para realizar a compra novamente",
					"no-reply@email.com", purchase.getProduct().getUser().getLogin());
		}
	}

}
