package ucsal.oferta.infraestrutura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ucsal.oferta.infraestrutura.model.Espaco;

@Repository
public interface EspacoRepository extends JpaRepository<Espaco, Long> {

}
