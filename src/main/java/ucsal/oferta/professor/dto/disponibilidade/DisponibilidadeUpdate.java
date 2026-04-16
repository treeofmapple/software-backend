package ucsal.oferta.professor.dto.disponibilidade;

import java.time.LocalTime;
import java.util.UUID;

public record DisponibilidadeUpdate(

		UUID id,
		String codigo,
	    String dia,
	    LocalTime inicio,
	    LocalTime fim

) {

}
