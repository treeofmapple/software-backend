package ucsal.oferta.security.dto.external;

import java.util.List;
import java.util.Set;

import ucsal.oferta.professor.dto.interesse.InteresseResponse;
import ucsal.oferta.professor.dto.internal.HorarioResponse;

public record ProfessorUtilsResponse(
		
		List<String> alternativas,
		Set<HorarioResponse> horarioAulas,
	    Set<InteresseResponse> interesses		
		
) {

}
