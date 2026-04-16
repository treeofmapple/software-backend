package ucsal.oferta.infraestrutura.model.validation;

import org.springframework.context.annotation.Configuration;

import ucsal.oferta.infraestrutura.dto.EspacoDTO;

@Configuration
public class EspacoValidation {
    public boolean existeId(EspacoDTO dto) {
        return dto.id() != null;
    }

}
