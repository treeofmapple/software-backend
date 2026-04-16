package ucsal.oferta.security.component;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.security.enums.TokenType;
import ucsal.oferta.security.model.Token;
import ucsal.oferta.security.model.User;
import ucsal.oferta.security.repository.TokenRepository;

@Component
@RequiredArgsConstructor
public class TokenComponent {

	private final TokenRepository tokenRepository;

	public void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(token);
	}

	public void revokeToken(String tokenValue) {
		tokenRepository.findByToken(tokenValue).ifPresent(token -> {
			token.setExpired(true);
			token.setRevoked(true);
			tokenRepository.save(token);
		});
	}

	public boolean isTokenRevoked(String tokenValue) {
		return tokenRepository.findByToken(tokenValue)
				.map(token -> token.isRevoked() || token.isExpired())
				.orElse(true);
	}

	public void revokeUserAllTokens(User user) {
		var validTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
		if (validTokens.isEmpty()) {
			return;
		}
		validTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validTokens);
	}

	public String resolveToken(String providedToken, HttpServletRequest request) {
		if (providedToken != null && !providedToken.isBlank()) {
			return providedToken;
		}
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

}
