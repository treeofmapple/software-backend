package ucsal.oferta.security.dto.admin;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AdminUserBan(

		@JsonAlias( {
			"userid",
			"userId" })
		UUID userId,
		
		String password
		
) {

}
