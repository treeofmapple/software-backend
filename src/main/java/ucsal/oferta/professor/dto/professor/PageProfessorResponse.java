package ucsal.oferta.professor.dto.professor;

import java.util.List;

public record PageProfessorResponse(
		List<ProfessorResponse> content,
		Integer page,
		Integer size,
		Integer totalPages
) {

}
