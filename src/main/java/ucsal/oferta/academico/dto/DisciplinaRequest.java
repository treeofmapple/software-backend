package ucsal.oferta.academico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DisciplinaRequest(
		
		@NotBlank(message = "Nome da disciplina não pode ficar vazio")
		String nome,
		
		@NotNull(message = "Carga horaria não pode ficar vazia")
		@Positive(message = "Carga horaria deve ser maior que 0")
		Integer cargaHoraria,
		
		@NotNull(message = "O semestre é obrigatório")
	    @Positive(message = "O semestre deve ser um valor positivo")
	    Integer semestre
		
) {

}
