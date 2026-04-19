package ucsal.oferta.infraestrutura.dto;

import ucsal.oferta.infraestrutura.model.TipoEquipamento;

public record EquipamentoDTO(
        Long id,
        String nome,
        TipoEquipamento tipoEquipamento,
        String descricao,
        String observacao
) {
	
}