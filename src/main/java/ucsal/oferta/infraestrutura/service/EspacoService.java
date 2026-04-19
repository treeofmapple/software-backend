package ucsal.oferta.infraestrutura.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ucsal.oferta.exception.sql.NotFoundException;
import ucsal.oferta.infraestrutura.dto.EspacoDTO;
import ucsal.oferta.infraestrutura.model.Espaco;
import ucsal.oferta.infraestrutura.model.validation.EspacoValidation;
import ucsal.oferta.infraestrutura.repository.EspacoRepository;

/**
 *
 * @author luisvso.dev@gmail.com
 * @date 15/08/2025
 *
 *       ```
 *       Write me later
 *       ```
 */

@Service
public class EspacoService {

    @Autowired
    private EspacoValidation espacoValidation;

    @Autowired
    private EspacoRepository espacoRepository;

    // cria uma lista de espaços fisicos
    public List<Espaco> create(List<EspacoDTO> dtos) {
        List<Espaco> espacosList = new ArrayList<Espaco>();
        for (EspacoDTO espacodto : dtos)
            espacosList.add(create(espacodto));
        return espacosList;
    }

    // salva o espaco fisico
    public Espaco create(EspacoDTO dto) {
        return espacoRepository.save(new Espaco(dto));
    }

    // atualiza o espaco fisico
    public Espaco update(EspacoDTO dto) {
        return (espacoValidation.existeId(dto) ? create(dto) : null);
    }

    // deleta o espaco fisico
    public Espaco delete(EspacoDTO dto) {
        // verifica se o espaco fisico existe
        if (espacoValidation.existeId(dto)) {
            // caso exista, a função deleta
            espacoRepository.delete(new Espaco(dto));
            return find(dto.id());
        }
        // caso não exista, ele retorna null
        return null;
    }

    // acha o Id do espaço
	@Transactional(readOnly = true)
	public Espaco find(Long query) {
		// caso o id do espaco exista, eu retorno o objeto espacoDto, caso não exista eu
		// retorno null
		return espacoRepository.findById(query)
				.orElseThrow(() -> new NotFoundException("Espaco com id: " + query + " não foi encontrado"));
	}

    public List<Espaco> findAll() {
        return espacoRepository.findAll();
    }

}
