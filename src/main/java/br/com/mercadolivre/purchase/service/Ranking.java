package br.com.mercadolivre.purchase.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.mercadolivre.purchase.model.Purchase;

@Service
public class Ranking implements SuccessPurchaseEvent {
	public void process(Purchase purchase) {
		var url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/ranking")
				.toUriString();
		RestTemplate rt = new RestTemplate();
		rt.postForEntity(url, null, null, purchase.getId(),
				purchase.getProduct().getUser().getId());
	}

}
