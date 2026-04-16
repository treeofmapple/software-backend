package ucsal.oferta.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.professor.dto.professor.ProfessorAlternativasResponse;
import ucsal.oferta.professor.dto.professor.ProfessorAlternativasUpdate;
import ucsal.oferta.professor.dto.professor.ProfessorRequest;
import ucsal.oferta.professor.dto.professor.ProfessorUpdateRequest;
import ucsal.oferta.professor.service.ProfessorService;
import ucsal.oferta.security.dto.user.PersonalUserResponse;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/professor")
@PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
public class ProfessorController {

	private final ProfessorService service;

	// TODO: All disponibilidade by pessoa

	// TODO: All Disciplina by pessoa

	// TODO: All interesses by pessoa

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/alternativas")
	@ResponseStatus(HttpStatus.OK)
	public ProfessorAlternativasResponse findAlternativasProfessor(@RequestParam(value = "id") UUID query) {
		return service.findProfessorAlternativas(query);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PersonalUserResponse registerProfessorInformations(@RequestBody @Valid ProfessorRequest request) {
		return service.registerProfessorInformations(request);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public PersonalUserResponse editProfessorInformations(@RequestBody @Valid ProfessorUpdateRequest request) {
		return service.updateProfessorInformations(request);
	}

	@PutMapping(value = "/alternativas")
	@ResponseStatus(HttpStatus.OK)
	public PersonalUserResponse editProfessorAlternativas(@RequestBody @Valid ProfessorAlternativasUpdate request) {
		return service.updateProfessorQuestions(request);
	}

}
