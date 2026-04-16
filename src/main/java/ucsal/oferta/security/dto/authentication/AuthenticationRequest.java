package ucsal.oferta.security.dto.authentication;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
		
		@NotBlank(message = "Email must be inserted")
		String email,
		
		@NotBlank(message = "Password must not be blank")
		String password
		
		) {

}
