package ucsal.oferta.professor.service.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ucsal.oferta.exception.sql.NotFoundException;
import ucsal.oferta.professor.model.InteresseDisciplina;
import ucsal.oferta.professor.repository.InteresseDisciplinaRepository;

@Component
@RequiredArgsConstructor
public class InteresseComponent {

	private final InteresseDisciplinaRepository interesseRepository;

	public InteresseDisciplina findById(UUID query) {
		return interesseRepository.findById(query).orElseThrow(
				() -> new NotFoundException(String.format("Interesse disciplina with id: %s, not found. ", query)));
	}

}
