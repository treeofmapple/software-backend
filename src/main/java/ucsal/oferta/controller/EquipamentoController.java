package ucsal.oferta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ucsal.oferta.infraestrutura.dto.EquipamentoDTO;
import ucsal.oferta.infraestrutura.model.Equipamento;
import ucsal.oferta.infraestrutura.service.EquipamentoService;

/**
 *
 *
 * @author luisvso.dev@gmail.com
 * @date 15/08/2025
 *
 *       ``` Write me later ```
 */
@RestController
@RequestMapping("/v1/equipamento")
// admin or professor

public class EquipamentoController {

	@Autowired
	private EquipamentoService equipamentoService;

	@GetMapping(value = "/{id}")
	public Equipamento find(@PathVariable(value = "id") Long query) {
		return equipamentoService.find(query);
	}

	@GetMapping("/all")
	public List<Equipamento> findAll() {
		return equipamentoService.findAll();
	}

	@PostMapping
	public Equipamento create(@RequestBody @Valid EquipamentoDTO dto) {
		return equipamentoService.create(dto);
	}

	@PostMapping("/multiple")
	public List<Equipamento> create(@RequestBody @Valid List<EquipamentoDTO> dtos) {
		return equipamentoService.create(dtos);
	}

	@PutMapping
	public Equipamento update(@RequestBody @Valid EquipamentoDTO dto) {
		return equipamentoService.update(dto);
	}

	@DeleteMapping
	public Equipamento delete(@RequestBody @Valid EquipamentoDTO dto) {
		return equipamentoService.delete(dto);
	}

}
