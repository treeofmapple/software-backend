package ucsal.oferta.infraestrutura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ucsal.oferta.infraestrutura.dto.EquipamentoDTO;

@Getter
@Setter
@Entity
public class Equipamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Enumerated(EnumType.STRING)
	private TipoEquipamento tipoEquipamento;

	// aqui pode ser uma descrição mais detalhada do equipamento
	private String descricao;

	// Inclui aqui o número de série, descrição complementar e outras coisas
	// necessárias
	private String observacao;

	public Equipamento() {
	}

	public Equipamento(String nome, TipoEquipamento tipoEquipamento, String descricao, String observacao) {
		this.nome = nome;
		this.tipoEquipamento = tipoEquipamento;
		this.descricao = descricao;
		this.observacao = observacao;
	}

	public Equipamento(EquipamentoDTO dto) {
		this.id = dto.id();
		this.nome = dto.nome();
		this.tipoEquipamento = dto.tipoEquipamento();
		this.descricao = dto.descricao();
		this.observacao = dto.observacao();
	}

}
