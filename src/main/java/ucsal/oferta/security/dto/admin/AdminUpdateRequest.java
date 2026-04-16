package ucsal.oferta.security.dto.admin;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AdminUpdateRequest(

		@JsonAlias( {
				"username", "nickname" })
		String nickname,
		String email

	){

}
