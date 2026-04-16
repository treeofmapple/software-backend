package ucsal.oferta.academico.dto;

import java.util.List;

public record PageDisciplinaResponse(
		
		List<DisciplinaResponse> content,
		Integer page,
		Integer size,
		Integer totalPages
		
) {

}
