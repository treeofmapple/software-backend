package ucsal.oferta.professor.dto.internal;

import ucsal.oferta.professor.model.enums.Dia;

public record HorarioResponse(
		
		String codigo,
		Dia dia,
        String inicio,
        String fim
		
) {

}
