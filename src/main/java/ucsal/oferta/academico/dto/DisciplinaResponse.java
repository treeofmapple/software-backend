package ucsal.oferta.academico.dto;

import java.util.UUID;

public record DisciplinaResponse(

		UUID id, 
		String codigo, 
		String nome, 
		Integer semestre,
		Integer cargaHoraria

) {
}
