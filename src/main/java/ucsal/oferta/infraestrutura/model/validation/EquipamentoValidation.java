package ucsal.oferta.infraestrutura.model.validation;

import org.springframework.context.annotation.Configuration;

import ucsal.oferta.infraestrutura.dto.EquipamentoDTO;

@Configuration
public class EquipamentoValidation {
    public boolean existeId(EquipamentoDTO dto) {
        return dto.id() != null;
    }

}
