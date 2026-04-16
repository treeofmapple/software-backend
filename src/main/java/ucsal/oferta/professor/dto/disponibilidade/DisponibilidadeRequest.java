package ucsal.oferta.professor.dto.disponibilidade;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DisponibilidadeRequest(

		@NotBlank(message = "Dia não pode ficar vazia")
		String dia,
		
	    @NotNull(message = "Hora inicial é obrigatória")
	    LocalTime inicio,

	    @NotNull(message = "Hora final é obrigatória")
	    LocalTime fim

) {

}
