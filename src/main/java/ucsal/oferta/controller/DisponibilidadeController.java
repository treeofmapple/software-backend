package ucsal.oferta.controller;

import java.time.LocalTime;
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
import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeRequest;
import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeResponse;
import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeUpdate;
import ucsal.oferta.professor.dto.disponibilidade.PageDisponibilidadeResponse;
import ucsal.oferta.professor.service.DisponibilidadeService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/professor/disponibilidade")
@PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
public class DisponibilidadeController {

	private final DisponibilidadeService disponibilidadeService;

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public DisponibilidadeResponse findDisponibilidadeById(@PathVariable(value = "id") UUID query) {
		return disponibilidadeService.findDisponibilidadeById(query);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/search")
	@ResponseStatus(HttpStatus.OK)
	public PageDisponibilidadeResponse findDisponibilidadeByParam(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false) String professorName, @RequestParam(required = false) LocalTime inicio,
			@RequestParam(required = false) LocalTime fim) {
		return disponibilidadeService.findDisponibilidadeByParams(page, professorName, inicio, fim);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DisponibilidadeResponse createDisponibilidade(@RequestBody @Valid DisponibilidadeRequest request) {
		return disponibilidadeService.createDisponibilidade(request);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public DisponibilidadeResponse updateDisponibilidade(@RequestBody @Valid DisponibilidadeUpdate request) {
		return disponibilidadeService.updateDisponibilidade(request);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDisponibilidade(@PathVariable(value = "id") UUID query) {
		disponibilidadeService.deleteDisponibilidade(query);
	}
}

//DONE: