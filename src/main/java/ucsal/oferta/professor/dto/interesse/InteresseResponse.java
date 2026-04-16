package ucsal.oferta.professor.dto.interesse;

import java.util.Set;
import java.util.UUID;

import ucsal.oferta.professor.dto.internal.DisciplinaDTO;

public record InteresseResponse(
		
		UUID id,
		String nickname,
		String nome,
		Set<DisciplinaDTO> disciplina,
		Integer cargaHorariaTotal
		
) {

}
