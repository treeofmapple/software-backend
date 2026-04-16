package ucsal.oferta.professor.model;

import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import ucsal.oferta.professor.model.enums.Dia;

@Embeddable
@Data
@AllArgsConstructor
public class HorarioAula {

	@Column(nullable = false, length = 36)
    private String codigo;
	
	@Enumerated(EnumType.STRING)
    private Dia dia;

	private LocalTime inicio;
	private LocalTime fim;
	
	public HorarioAula() {
		this.codigo = UUID.randomUUID().toString();
	}
	
}
