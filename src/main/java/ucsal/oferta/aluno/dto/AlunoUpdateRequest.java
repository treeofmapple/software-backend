package ucsal.oferta.aluno.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public record AlunoUpdateRequest(
		
		@JsonAlias( {
				"username",
				"nickname" })
		String nickname,
		
		String email,
		
		String nome,
		
		String telefone,
		
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
		@JsonAlias( {
				"dataNascimento", "nascimento", "data" })
		LocalDate dataNascimento
		
) {

}
