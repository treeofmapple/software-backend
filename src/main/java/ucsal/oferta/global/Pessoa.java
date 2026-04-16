package ucsal.oferta.global;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ucsal.oferta.security.model.User;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pessoa")
public abstract class Pessoa {

	@Id
	private UUID id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private User user;

	@Column(name = "nome", nullable = true, length = 150)
	private String nome;

	@Column(name = "telefone", length = 20, nullable = true)
	private String telefone;

	@Column(name = "data_nascimento", unique = false, updatable = true, nullable = true)
	private LocalDate dataNascimento;

	@Column(name = "cadastro_completo", nullable = false)
	private boolean cadastroCompleto = false;

	@PrePersist
	@PreUpdate
	public void updateCadastroStatus() {
		this.cadastroCompleto = (this.dataNascimento != null && this.telefone != null && this.nome != null);
	}

}
