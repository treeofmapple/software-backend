package ucsal.oferta.infraestrutura.dto;

import java.util.List;

import ucsal.oferta.infraestrutura.model.Equipamento;
import ucsal.oferta.infraestrutura.model.TipoEspaco;

public record EspacoDTO(
    Long id,
    String sigla,
    String descricao,
    int capacidadeMaxima,
    List<Equipamento> equipamentos,
    String localizacao,
    TipoEspaco tipoEspaco,
    String observacao
) {}