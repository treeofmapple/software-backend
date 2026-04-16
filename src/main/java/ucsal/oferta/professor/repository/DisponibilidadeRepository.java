package ucsal.oferta.professor.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ucsal.oferta.professor.model.Disponibilidade;

@Repository
public interface DisponibilidadeRepository
		extends JpaRepository<Disponibilidade, UUID>, JpaSpecificationExecutor<Disponibilidade> {
}
