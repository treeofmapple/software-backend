package ucsal.oferta.professor.dto.interesse;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record InteresseRequest(
		
	    @NotEmpty(message = "Disciplinas must not be empty")
	    List<String> disciplinas

		) {

}
