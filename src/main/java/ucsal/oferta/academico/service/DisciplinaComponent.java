package ucsal.oferta.academico.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ucsal.oferta.academico.model.Disciplina;
import ucsal.oferta.academico.repository.DisciplinaRepository;
import ucsal.oferta.exception.sql.DataViolationException;
import ucsal.oferta.exception.sql.NotFoundException;

@Component
@RequiredArgsConstructor
public class DisciplinaComponent {

	private final DisciplinaRepository repository;

	public Disciplina findDisciplinaById(UUID query) {
		return repository.findById(query).orElseThrow(
				() -> new NotFoundException(String.format("Disciplina with id: %s, doens't exist.", query)));
	}

	public void ensureNomeAreUnique(String nome) {
		if(repository.existsByNome(nome)) {
			throw new DataViolationException("Nome is already in use: " + nome);
		}
	}
	
}
