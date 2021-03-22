package br.com.mercadolivre.security.configuration;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenManager tokenManager;
	private final UsersService usersService;

	public JwtAuthenticationFilter(TokenManager tokenManager, UsersService usersService) {
		this.tokenManager = tokenManager;
		this.usersService = usersService;

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Optional<String> possibleToken = getTokenFrom(request);

		if (possibleToken.isPresent() && tokenManager.isValid(possibleToken.get())) {
			var username = tokenManager.getUserName(possibleToken.get());
			var userDetails = usersService.loadUserByUsername(username);

			var authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);

	}

	private Optional<String> getTokenFrom(HttpServletRequest request) {
		var token = request.getHeader("Authorization");
		return Optional.ofNullable(token);
	}

}
