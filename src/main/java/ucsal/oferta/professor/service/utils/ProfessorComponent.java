package ucsal.oferta.professor.service.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ucsal.oferta.exception.sql.NotFoundException;
import ucsal.oferta.professor.model.Professor;
import ucsal.oferta.professor.repository.ProfessorRepository;

@Component
@RequiredArgsConstructor
public class ProfessorComponent {

	private final ProfessorRepository professorRepository;
	
	public Professor findById(UUID query) {
		return professorRepository.findActiveAndNotBlockedById(query)
				.orElseThrow(() -> new NotFoundException(String.format("Professor with id: %s was not found.", query)));
	}
	
	
}
