package ucsal.oferta.professor.dto.disponibilidade;

import java.util.Set;
import java.util.UUID;

import ucsal.oferta.professor.dto.internal.HorarioResponse;

public record DisponibilidadeResponse(
	    
		UUID id,
		String nickname,
		String nome,
	    Set<HorarioResponse> horarioAulas
		
) {

}
