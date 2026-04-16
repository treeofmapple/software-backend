package ucsal.oferta.professor.model;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disponibilidade")
public class Disponibilidade {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne
	@JoinColumn(name = "professor_id", nullable = false)
	private Professor professor;
	
	@ElementCollection
    @CollectionTable(
        name = "disponibilidade_horarios", 
        joinColumns = @JoinColumn(name = "disponibilidade_id")
    )
	private Set<HorarioAula> horarioAulas;

}
