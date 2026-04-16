package ucsal.oferta.professor.service.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ucsal.oferta.exception.sql.NotFoundException;
import ucsal.oferta.professor.model.Disponibilidade;
import ucsal.oferta.professor.repository.DisponibilidadeRepository;

@Component
@RequiredArgsConstructor
public class DisponibilidadeComponent {

	private final DisponibilidadeRepository disponibilidadeRepository;

	public Disponibilidade findById(UUID query) {
		return disponibilidadeRepository.findById(query)
				.orElseThrow(() -> new NotFoundException(String.format("Disciplina with id: %s not found", query)));
	}

}
