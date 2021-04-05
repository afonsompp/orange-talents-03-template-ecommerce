package br.com.mercadolivre.purchase.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReciveFakeRequestsController {

	@PostMapping(value = "/invoice")
	public void invoice() {
		// recebe requisições fake
	}

	@PostMapping(value = "/ranking")
	public void ranking() {
		// recebe requisições fake
	}
}
