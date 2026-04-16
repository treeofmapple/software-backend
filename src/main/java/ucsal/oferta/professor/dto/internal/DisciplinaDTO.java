package ucsal.oferta.professor.dto.internal;

import java.util.UUID;

public record DisciplinaDTO(
		
		UUID id,
		String codigo,
		String nome,
		Integer semestre,
		Integer cargaHoraria
		
) {

}
