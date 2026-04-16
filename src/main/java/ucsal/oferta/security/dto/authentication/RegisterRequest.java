package ucsal.oferta.security.dto.authentication;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		
		@JsonAlias({"username", "nickname"})
	    @NotBlank(message = "Username must not be blank")
	    @Size(min = 4, max = 40, message = "Username must be at least 4 characters and limited to 40")
		String nickname,

		@NotBlank(message = "Email must not be blank")
	    @Email(message = "Email must be valid")
	    String email,

	    @NotBlank(message = "Password must not be blank")
	    @Size(min = 8, message = "Password must be at least 8 characters")
		String password,
		
		@JsonAlias({"confirmPassword", "confimationPassword"})
	    @NotBlank(message = "Confirm password must not be blank")		
		String confirmPassword
		
) {

}
