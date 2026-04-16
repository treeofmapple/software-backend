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
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.academico.dto.DisciplinaRequest;
import ucsal.oferta.academico.dto.DisciplinaResponse;
import ucsal.oferta.academico.dto.DisciplinaUpdate;
import ucsal.oferta.academico.dto.PageDisciplinaResponse;
import ucsal.oferta.academico.service.DisciplinaService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/disciplina")
@PreAuthorize("hasAnyRole('ADMIN')")
public class DisciplinaController {

	private final DisciplinaService service;

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public DisciplinaResponse findDisciplinaById(@PathVariable(value = "id") UUID query) {
		return service.findDisciplinaById(query);
	}

	@GetMapping(value = "/search")
	@ResponseStatus(HttpStatus.OK)
	public PageDisciplinaResponse searchDisciplinaByParams(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false) String name, @RequestParam(required = false) String codigo,
			@RequestParam(required = false, value = "cargaMin") @Min(0) Integer cargaHorariaMin,
			@RequestParam(required = false, value = "cargaMax") Integer cargaHorariaMax) {
		return service.findDisciplinaByParams(page, name, codigo, cargaHorariaMin, cargaHorariaMax);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DisciplinaResponse createDisciplina(@RequestBody @Valid DisciplinaRequest request) {
		return service.createDisciplina(request);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public DisciplinaResponse updateDisciplina(@RequestBody @Valid DisciplinaUpdate request) {
		return service.updateDisciplina(request);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDisciplina(@PathVariable(value = "id") UUID query) {
		service.deleteById(query);
	}

}

