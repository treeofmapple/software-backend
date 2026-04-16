package ucsal.oferta.security.dto.user;

import java.util.UUID;

public record UserResponse(
		
		UUID id,
		String nickname,
		String email,
		String role

) {

}
