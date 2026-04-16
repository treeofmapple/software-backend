package ucsal.oferta.professor.dto.professor;

import java.time.LocalDate;
import java.util.Set;

import ucsal.oferta.professor.dto.interesse.InteresseResponse;
import ucsal.oferta.professor.dto.internal.HorarioResponse;

public record ProfessorResponse(

		String nickname,
		String nome,
		String email,
		String telefone,
		LocalDate dataNascimento,
		Set<HorarioResponse> horarioAulas,
	    Set<InteresseResponse> interesses
		
) {

}
