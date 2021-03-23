package br.com.mercadolivre.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.mercadolivre.security.UserAuthenticationRequest;

@RestController
@RequestMapping("/auth")
public class UserAuthenticatorController {

	private final AuthenticationManager authManager;
	private final TokenManager tokenManager;

	public UserAuthenticatorController(AuthenticationManager authManager,
			TokenManager tokenManager) {
		this.authManager = authManager;
		this.tokenManager = tokenManager;
	}

	private static final Logger log = LoggerFactory
			.getLogger(UserAuthenticatorController.class);

	@PostMapping
	public ResponseEntity<String> authenticate(
			@RequestBody UserAuthenticationRequest loginInfo) {

		var authenticationToken = loginInfo.build();

		try {
			Authentication authentication = authManager.authenticate(authenticationToken);
			var jwt = tokenManager.generateToken(authentication);

			return ResponseEntity.ok(jwt);

		} catch (AuthenticationException e) {
			log.error("[Authentication] {}", e);
			return ResponseEntity.badRequest().build();
		}

	}
}
