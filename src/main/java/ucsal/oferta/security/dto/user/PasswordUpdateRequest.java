package ucsal.oferta.security.dto.user;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordUpdateRequest(
		
		@JsonAlias( {
				"current",
				"currentPassword" })
    @NotBlank(message = "Current password must not be blank")
    String currentPassword,

		@JsonAlias({ "newPassword",
				"password" })
    @NotBlank(message = "New password must not be blank")
    @Size(min = 6, message = "New password must be at least 6 characters")
    String newPassword,

		@JsonAlias({ "confirmPassword",
				"confirmationPassword" })
	@NotBlank(message = "Confirmation password must not be blank")
    String confirmPassword
	
		
) {

}
