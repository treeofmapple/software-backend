package ucsal.oferta.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.professor.dto.interesse.InteresseRequest;
import ucsal.oferta.professor.dto.interesse.InteresseResponse;
import ucsal.oferta.professor.dto.interesse.InteresseUpdate;
import ucsal.oferta.professor.dto.interesse.PageInteresseResponse;
import ucsal.oferta.professor.service.InteresseService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/professor/interesse")
@PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
public class InteresseController {

	private final InteresseService interesseService;

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public InteresseResponse findInteressesById(@PathVariable(value = "id") UUID query) {
		return interesseService.findById(query);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/search")
	@ResponseStatus(HttpStatus.OK)
	public PageInteresseResponse findInteressesByPage(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false) String disciplinaName,
			@RequestParam(required = false) String professorName) {
		return interesseService.findInteresseDisciplinaByPage(page, disciplinaName, professorName);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InteresseResponse createInteresse(@RequestBody @Valid InteresseRequest request) {
		return interesseService.createInteresseDisciplina(request);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public InteresseResponse updateInteresse(@RequestBody @Valid InteresseUpdate request) {
		return interesseService.updateInteresseDisciplina(request);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteInteresse(@PathVariable(name = "id") UUID query) {
		interesseService.deleteInteresseDisciplinaById(query);
	}

}

// DONE: