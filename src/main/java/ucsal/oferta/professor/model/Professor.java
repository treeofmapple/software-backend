package ucsal.oferta.professor.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ucsal.oferta.global.Pessoa;
import ucsal.oferta.logic.common.ListToJsonConversion;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "professor")
public class Professor extends Pessoa {

	@Convert(converter = ListToJsonConversion.class)
	@Column(name = "alternativas", columnDefinition = "TEXT", nullable = true)
	private List<String> alternativas;

	@OneToOne(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Disponibilidade disponibilidades;

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<InteresseDisciplina> interesses;
	
}
	