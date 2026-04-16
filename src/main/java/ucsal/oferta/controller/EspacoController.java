package ucsal.oferta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ucsal.oferta.infraestrutura.dto.EspacoDTO;
import ucsal.oferta.infraestrutura.model.Espaco;
import ucsal.oferta.infraestrutura.service.EspacoService;

/**
 *
 * @author luisvso.dev@gmail.com
 * @date 15/08/2025
 */

@RestController
@RequestMapping("/espaco")
// admin
public class EspacoController {

	@Autowired
	private EspacoService espacoService;

	@GetMapping
	public Espaco find(@RequestBody @Valid EspacoDTO dto) {
		return espacoService.find(dto);
	}

	@GetMapping("/all")
	public List<Espaco> findAll() {
		return espacoService.findAll();
	}

	@PostMapping
	public Espaco create(@RequestBody @Valid EspacoDTO dto) {
		return espacoService.create(dto);
	}

	@PostMapping("all")
	public List<Espaco> create(@RequestBody @Valid List<EspacoDTO> dtos) {
		return espacoService.create(dtos);
	}

	@PutMapping
	public Espaco update(@RequestBody @Valid EspacoDTO dto) {
		return espacoService.update(dto);
	}

	@DeleteMapping
	public Espaco delete(@RequestBody @Valid EspacoDTO dto) {
		return espacoService.delete(dto);
	}

}
