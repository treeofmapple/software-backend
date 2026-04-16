package ucsal.oferta.security.dto.user;

import jakarta.validation.constraints.NotBlank;

public record DeleteAccountRequest (
		
	    @NotBlank(message = "Password must not be blank")
		String password
		
) {

}
