package ucsal.oferta.aluno.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlunoRequest(
		
		@NotBlank(message = "O nome é obrigatório")
	    @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
		String nome,
		
		@NotBlank(message = "O telefone é obrigatório")
	    @Size(min = 10, max = 20, message = "O telefone deve ter entre 10 e 20 caracteres")
		String telefone,
		
		@NotNull(message = "A data de nascimento é obrigatória")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
		@JsonAlias( {
				"dataNascimento", "nascimento", "data" })
		LocalDate dataNascimento

){

}
