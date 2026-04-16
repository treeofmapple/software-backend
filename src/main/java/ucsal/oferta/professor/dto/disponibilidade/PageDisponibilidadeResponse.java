package ucsal.oferta.professor.dto.disponibilidade;

import java.util.List;

public record PageDisponibilidadeResponse(

		List<DisponibilidadeResponse> content,
		Integer page,
		Integer size,
		Integer totalPages

) {

}
