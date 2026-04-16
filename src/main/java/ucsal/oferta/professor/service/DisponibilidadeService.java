package ucsal.oferta.professor.service;

import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.exception.security.AccessDeniedException;
import ucsal.oferta.exception.sql.DataViolationException;
import ucsal.oferta.exception.sql.NotFoundException;
import ucsal.oferta.logic.security.SecurityUtils;
import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeRequest;
import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeResponse;
import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeUpdate;
import ucsal.oferta.professor.dto.disponibilidade.PageDisponibilidadeResponse;
import ucsal.oferta.professor.mapper.DisponibilidadeMapper;
import ucsal.oferta.professor.mapper.HorarioAulaMapper;
import ucsal.oferta.professor.model.Disponibilidade;
import ucsal.oferta.professor.repository.DisponibilidadeRepository;
import ucsal.oferta.professor.repository.Specification.DisponibilidadeSpecification;
import ucsal.oferta.professor.service.utils.DisponibilidadeComponent;
import ucsal.oferta.professor.service.utils.ProfessorComponent;

@Log4j2
@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

	@Value("${application.size.page:10}")
	private int PAGE_SIZE;

	private final DisponibilidadeRepository disponibilidadeRepository;
	private final DisponibilidadeMapper disponibilidadeMapper;
	private final HorarioAulaMapper horarioMapper;
	private final DisponibilidadeComponent disponibilidadeUtils;
	private final ProfessorComponent professorUtils;
	private final SecurityUtils securityUtils;

	@Transactional(readOnly = true) 
	public PageDisponibilidadeResponse findDisponibilidadeByParams(int page, String professorName, LocalTime inicio,
			LocalTime fim) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		Specification<Disponibilidade> spec = DisponibilidadeSpecification.findByCriteria(professorName, inicio, fim);
		var response = disponibilidadeRepository.findAll(spec, pageable);
		return disponibilidadeMapper.toResponse(response);
	}
	
	@Transactional(readOnly = true)
	public DisponibilidadeResponse findDisponibilidadeById(UUID query) {
		var response = disponibilidadeUtils.findById(query);
		return disponibilidadeMapper.toResponse(response);
	}
	
	// Exemplo: Segunda 08:00 - 12:00, Segunda 18:00 - 21:00
	@Transactional
	public DisponibilidadeResponse createDisponibilidade(DisponibilidadeRequest request) {
		var systemUser = securityUtils.getUser();
		var professor = professorUtils.findById(systemUser.getPessoa().getId()); 
		var response = disponibilidadeMapper.build(request, professor);
		disponibilidadeRepository.save(response);
		return disponibilidadeMapper.toResponse(response);
	}

	@Transactional
	public DisponibilidadeResponse updateDisponibilidade(DisponibilidadeUpdate request) {
		var systemUser = securityUtils.getUser();
		var professor = professorUtils.findById(systemUser.getPessoa().getId());
		var disponibilidade = disponibilidadeUtils.findById(request.id());
		
		if (!disponibilidade.getProfessor().getId().equals(professor.getId())) {
			throw new AccessDeniedException("You can only edit your own availability.");
		}

		if (request.codigo() != null && !request.codigo().isBlank()) {
            var slot = disponibilidade.getHorarioAulas().stream()
                    .filter(h -> h.getCodigo().equals(request.codigo()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Time slot not found"));
            disponibilidade.getHorarioAulas().remove(slot);
            horarioMapper.update(slot, request);
            disponibilidade.getHorarioAulas().add(slot);
        } 
        else {
            var novoSlot = horarioMapper.build(request);
            
            if (disponibilidade.getHorarioAulas().contains(novoSlot)) {
                throw new DataViolationException("This time slot already exists");
            }
            
            disponibilidade.getHorarioAulas().add(novoSlot);
        }

		var saved = disponibilidadeRepository.save(disponibilidade);
		return disponibilidadeMapper.toResponse(saved);
	}

	@Transactional
	public void deleteDisponibilidade(UUID query) {
		var systemUser = securityUtils.getUser();
		var disponibilidade = disponibilidadeUtils.findById(query);
		
		if (!systemUser.getPessoa().equals(disponibilidade.getProfessor())) {
			throw new AccessDeniedException("You can only edit your own availability.");
		}

		disponibilidadeRepository.deleteById(disponibilidade.getId());
	}

}
