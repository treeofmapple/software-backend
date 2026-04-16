package ucsal.oferta.security.dto.authentication;

public record AuthenticationResponse(
		
		String accessToken,

		String refreshToken
		
		) {

}
