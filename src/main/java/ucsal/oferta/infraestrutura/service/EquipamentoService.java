package ucsal.oferta.infraestrutura.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ucsal.oferta.infraestrutura.dto.EquipamentoDTO;
import ucsal.oferta.infraestrutura.model.Equipamento;
import ucsal.oferta.infraestrutura.model.validation.EquipamentoValidation;
import ucsal.oferta.infraestrutura.repository.EquipamentoRepository;

/**
 * A one-line summary.
 *
 *
 * @author luisvso.dev@gmail.com
 * @date 15/08/2025
 *
 *       ``` Write me later ```
 */

@Service
public class EquipamentoService {

	@Autowired
	EquipamentoValidation equipamentoValidation;

	@Autowired
	EquipamentoRepository equipamentoRepository;

	// cria uma lista de equipamentos
	public List<Equipamento> create(List<EquipamentoDTO> dtos) {
		List<Equipamento> equipamentosList = new ArrayList<Equipamento>();
		for (EquipamentoDTO equipamentodto : dtos)
			equipamentosList.add(create(equipamentodto));
		return equipamentosList;
	}

	// cria o equipamento e salva no banco de dados
	public Equipamento create(EquipamentoDTO dto) {
		return equipamentoRepository.save(new Equipamento(dto));
	}

	// atualiza o equipamento
	public Equipamento update(EquipamentoDTO dto) {
		// faz verificação se o equipamento foi criado
		return (equipamentoValidation.existeId(dto) ? create(dto) : null);
	}

	// deleta o equipamento
	public Equipamento delete(EquipamentoDTO dto) {
		// deleta somente se o equipamento existir
		if (equipamentoValidation.existeId(dto)) {
			equipamentoRepository.delete(new Equipamento(dto));
			return find(dto);
		}
		return null;
	}

	// acha apensar um equipamento
	public Equipamento find(EquipamentoDTO dto) {
		return (equipamentoValidation.existeId(dto) ? equipamentoRepository.findById(dto.id()).get() : null);
	}

	// retorna uma lista de equipamentos
	public List<Equipamento> findAll() {
		return equipamentoRepository.findAll();
	}

}
