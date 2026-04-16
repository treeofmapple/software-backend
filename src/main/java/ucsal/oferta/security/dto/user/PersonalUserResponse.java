package ucsal.oferta.security.dto.user;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;

public record PersonalUserResponse(
		
		UUID id,
	    String nickname,
	    String email,
	    String role,
	    
	    String nome,
	    String telefone,
	    LocalDate dataNascimento,

	    @JsonAlias({
				"showCompletionAlert",
				"showAlert" })
	    Boolean showAlert,
	    
	    @JsonInclude(JsonInclude.Include.NON_NULL)
	    Object details
		
) {

}
