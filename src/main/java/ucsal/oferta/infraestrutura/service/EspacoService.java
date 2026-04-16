package ucsal.oferta.infraestrutura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucsal.oferta.infraestrutura.dto.EspacoDTO;
import ucsal.oferta.infraestrutura.model.Espaco;
import ucsal.oferta.infraestrutura.model.validation.EspacoValidation;
import ucsal.oferta.infraestrutura.repository.EspacoRepository;

import java.util.ArrayList;
import java.util.List;

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
            return find(dto);
        }
        // caso não exista, ele retorna null
        return null;
    }

    // acha o Id do espaço
    public Espaco find(EspacoDTO dto) {
        // caso o id do espaco exista, eu retorno o objeto espacoDto, caso não exista eu
        // retorno null
        return (espacoValidation.existeId(dto) ? espacoRepository.findById(dto.id()).get() : null);
    }

    public List<Espaco> findAll() {
        return espacoRepository.findAll();
    }

}
