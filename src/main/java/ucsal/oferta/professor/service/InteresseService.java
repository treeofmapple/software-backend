package ucsal.oferta.professor.service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.academico.model.Disciplina;
import ucsal.oferta.academico.repository.DisciplinaRepository;
import ucsal.oferta.exception.security.AccessDeniedException;
import ucsal.oferta.exception.sql.DataViolationException;
import ucsal.oferta.exception.sql.NotFoundException;
import ucsal.oferta.logic.security.SecurityUtils;
import ucsal.oferta.professor.dto.interesse.InteresseRequest;
import ucsal.oferta.professor.dto.interesse.InteresseResponse;
import ucsal.oferta.professor.dto.interesse.InteresseUpdate;
import ucsal.oferta.professor.dto.interesse.PageInteresseResponse;
import ucsal.oferta.professor.mapper.InteresseMapper;
import ucsal.oferta.professor.model.InteresseDisciplina;
import ucsal.oferta.professor.repository.InteresseDisciplinaRepository;
import ucsal.oferta.professor.repository.Specification.InteresseSpecification;
import ucsal.oferta.professor.service.utils.InteresseComponent;
import ucsal.oferta.professor.service.utils.ProfessorComponent;

@Log4j2
@Service
@RequiredArgsConstructor
public class InteresseService {

	@Value("${application.size.page:10}")
	private int PAGE_SIZE;

	private final InteresseDisciplinaRepository interesseRepository;
	private final DisciplinaRepository disciplinaRepository;
	private final InteresseMapper interesseMapper;
	private final InteresseComponent interesseUtils;
	private final ProfessorComponent professorUtils;
	private final SecurityUtils securityUtils;

	@Transactional(readOnly = true)
	public InteresseResponse findById(UUID query) {
		var response = interesseUtils.findById(query);
		return interesseMapper.toResponse(response);
	}

	@Transactional(readOnly = true)
	public PageInteresseResponse findInteresseDisciplinaByPage(int page, String disciplina, String professor) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		Specification<InteresseDisciplina> spec = InteresseSpecification.findByCriteria(professor, disciplina);
		var response = interesseRepository.findAll(spec, pageable);
		return interesseMapper.toResponse(response);
	}

	@Transactional
	public InteresseResponse createInteresseDisciplina(InteresseRequest request) {
		var systemUser = securityUtils.getUser();
		var professor = professorUtils.findById(systemUser.getPessoa().getId());
		var foundDisciplinas = disciplinaRepository.findByNomeIn(request.disciplinas());

		if (foundDisciplinas.isEmpty()) {
			throw new NotFoundException("None of the requested disciplinas were found");
		}

		Set<Disciplina> disciplinas = foundDisciplinas.stream()
				.filter(d -> !interesseRepository.existsByProfessor_IdAndDisciplina_Id(professor.getId(), d.getId()))
				.collect(Collectors.toSet());

		if (disciplinas.isEmpty()) {
			throw new DataViolationException("Interesse already exists for all provided disciplinas");
		}

		var interesse = interesseMapper.build(professor, disciplinas);
		interesseRepository.save(interesse);
		return interesseMapper.toResponse(interesse);
	}

	@Transactional
	public InteresseResponse updateInteresseDisciplina(InteresseUpdate request) {
		var systemUser = securityUtils.getUser();
		var professor = professorUtils.findById(systemUser.getPessoa().getId());
		var interesse = interesseUtils.findById(request.id());

		if (!systemUser.getPessoa().getId().equals(interesse.getProfessor().getId())) {
			throw new AccessDeniedException("You are not allowed to delete this interesse disciplina");
		}

		var foundDisciplinas = disciplinaRepository.findByNomeIn(request.disciplinas());

		if (foundDisciplinas.isEmpty()) {
			throw new NotFoundException("None of the requested disciplinas were found");
		}

		Set<Disciplina> disciplinas = foundDisciplinas.stream()
				.filter(d -> !interesseRepository.existsByProfessor_IdAndDisciplina_Id(professor.getId(), d.getId()))
				.collect(Collectors.toSet());

		if (disciplinas.isEmpty()) {
			throw new DataViolationException("Interesse already exists for all provided disciplinas");
		}

		interesse.getDisciplina().clear();
		interesseMapper.update(interesse, professor, disciplinas);
		interesseRepository.save(interesse);
		return interesseMapper.toResponse(interesse);
	}

	@Transactional
	public void deleteInteresseDisciplinaById(UUID query) {
		var systemUser = securityUtils.getUser();
		var response = interesseUtils.findById(query);

		if (!systemUser.getPessoa().getId().equals(response.getProfessor().getId())) {
			throw new AccessDeniedException("You are not allowed to delete this interesse disciplina");
		}

		interesseRepository.deleteById(response.getId());
	}

}
