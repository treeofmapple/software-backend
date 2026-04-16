package ucsal.oferta.professor.dto.interesse;

import java.util.List;
import java.util.UUID;

public record InteresseUpdate(

		UUID id,
		List<String> disciplinas 

) {

}
