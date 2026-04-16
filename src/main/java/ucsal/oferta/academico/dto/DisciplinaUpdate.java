package ucsal.oferta.academico.dto;

import java.util.UUID;

public record DisciplinaUpdate(

		UUID disciplinaId,
		
		String nome,

		Integer cargaHoraria,
		
		Integer semestre,
		
		Boolean generateNewCode

) {

}
