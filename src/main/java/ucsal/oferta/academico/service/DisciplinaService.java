package ucsal.oferta.academico.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.academico.dto.DisciplinaRequest;
import ucsal.oferta.academico.dto.DisciplinaResponse;
import ucsal.oferta.academico.dto.DisciplinaUpdate;
import ucsal.oferta.academico.dto.PageDisciplinaResponse;
import ucsal.oferta.academico.mapper.DisciplinaMapper;
import ucsal.oferta.academico.model.Disciplina;
import ucsal.oferta.academico.repository.DisciplinaRepository;
import ucsal.oferta.academico.repository.DisciplinaSpecification;

@Log4j2
@Service
@RequiredArgsConstructor
public class DisciplinaService {

	@Value("${application.size.page:20}")
	private int PAGE_SIZE;

	private final DisciplinaRepository repository;
	private final DisciplinaMapper mapper;
	private final DisciplinaComponent component;

	@Transactional(readOnly = true)
	public DisciplinaResponse findDisciplinaById(UUID query) {
		var response = component.findDisciplinaById(query);
		return mapper.toResponse(response);
	}

	@Transactional(readOnly = true)
	public PageDisciplinaResponse findDisciplinaByParams(int page, String name, String codigo, Integer cargaHorariaMin,
			Integer cargaHorariaMax) {
		if (cargaHorariaMin != null && cargaHorariaMax != null && cargaHorariaMin > cargaHorariaMax) {
			throw new IllegalArgumentException("Min cannot be greater than Max");
		}
		Specification<Disciplina> spec = DisciplinaSpecification.findByCriteria(name, codigo, cargaHorariaMin,
				cargaHorariaMax);
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		Page<Disciplina> disciplina = repository.findAll(spec, pageable);
		return mapper.toResponse(disciplina);
	}

	@Transactional
	public DisciplinaResponse createDisciplina(DisciplinaRequest request) {
		component.ensureNomeIsUnique(request.nome());
		component.ensureCodigoIsUnique(request.codigo());
		var disciplina = mapper.build(request);
		repository.save(disciplina);
		return mapper.toResponse(disciplina);
	}

	@Transactional
	public DisciplinaResponse updateDisciplina(DisciplinaUpdate request) {
		var disciplina = component.findDisciplinaById(request.disciplinaId());
		mapper.update(disciplina, request);
		repository.save(disciplina);
		return mapper.toResponse(disciplina);
	}

	@Transactional
	public void deleteById(UUID query) {
		var disciplina = component.findDisciplinaById(query);
		repository.deleteById(disciplina.getId());
	}

}
