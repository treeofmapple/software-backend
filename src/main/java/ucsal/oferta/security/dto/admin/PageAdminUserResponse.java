package ucsal.oferta.security.dto.admin;

import java.util.List;

public record PageAdminUserResponse(
		
		List<AdminUserResponse> content,
		Integer page,
		Integer size,
		Integer totalPages
		
) {

}
