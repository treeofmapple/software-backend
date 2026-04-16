package ucsal.oferta.professor.dto.professor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ProfessorUpdateRequest(
		
		String nickname,
		
		String email,
		
		String nome,
		
		String telefone,
		
		@JsonAlias( {
				"dataNascimento", "nascimento", "data" })
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
		LocalDate dataNascimento
		
) {

}
