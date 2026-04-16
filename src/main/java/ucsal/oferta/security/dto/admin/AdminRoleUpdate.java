package ucsal.oferta.security.dto.admin;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import ucsal.oferta.security.enums.Role;

public record AdminRoleUpdate(
		
		@JsonAlias( {
				"userid",
				"userId" })
		UUID userId,
		
		@NotBlank(message = "Role name can't be null")
		Role role
		
) {

}
