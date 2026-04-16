package ucsal.oferta.professor.dto.interesse;

import java.util.List;

public record PageInteresseResponse(
		List<InteresseResponse> content,
		Integer page,
		Integer size,
		Integer totalPages
	) {

}
