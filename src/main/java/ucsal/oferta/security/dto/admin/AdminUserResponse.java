package ucsal.oferta.security.dto.admin;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AdminUserResponse(
		
		UUID id,
	    String nickname,
	    String email,
	    String role,

	    String nome,
	    String telefone,
	    LocalDate dataNascimento,
	    
	    @JsonProperty("contaNaoBloqueiada")
	    Boolean accountNonLocked,
	    
	    @JsonProperty("contaHabilitada")
	    Boolean enabled,
	    
	    @JsonInclude(JsonInclude.Include.NON_NULL)
		Object details,
		
		String createdBy,
		String lastModifiedBy,
		
	    ZonedDateTime createdAt,
	    ZonedDateTime updatedAt
		
) {

}
