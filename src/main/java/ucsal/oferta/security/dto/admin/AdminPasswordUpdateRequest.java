package ucsal.oferta.security.dto.admin;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminPasswordUpdateRequest(
		
		@JsonAlias( {
			"userid",
			"userId" })
		UUID userId,
		
		@JsonAlias({ "password",
				"adminPassword" })
		@NotBlank(message = "Admin password must not be blank")
		String password,
		
		@JsonAlias({ "newPassword",
				"password" })
	    @NotBlank(message = "New password must not be blank")
	    @Size(min = 8, message = "New password must be at least 8 characters")
	    String newPassword,

		@JsonAlias({ "confirmPassword",
				"confirmationPassword" })
		@NotBlank(message = "Confirmation password must not be blank")
	    String confirmPassword
		
) {

}
