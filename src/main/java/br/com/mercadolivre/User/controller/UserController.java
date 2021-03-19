package br.com.mercadolivre.User.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.mercadolivre.User.dto.UserRequest;
import br.com.mercadolivre.User.repository.UserRepository;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;

	}

	@PostMapping()
	public ResponseEntity<?> postMethodName(@RequestBody @Valid UserRequest request) {
		var user = request.parseToUser();
		userRepository.save(user);

		return ResponseEntity.ok().build();
	}

}
