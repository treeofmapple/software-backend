package ucsal.oferta.professor.dto.professor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import ucsal.oferta.professor.dto.interesse.InteresseResponse;
import ucsal.oferta.professor.dto.internal.HorarioResponse;

public record ProfessorAlternativasResponse(

		String nickname,
		String email,
		String nome,
		String telefone,
		LocalDate dataNascimento,
		List<String> alternativas,
		Set<HorarioResponse> horarioAulas,
	    Set<InteresseResponse> interesses
		
) {

}
