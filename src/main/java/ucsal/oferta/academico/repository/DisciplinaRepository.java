package ucsal.oferta.academico.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ucsal.oferta.academico.model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, UUID>, JpaSpecificationExecutor<Disciplina> {

	Optional<Disciplina> findByCodigo(String codigo);
	
	Optional<Disciplina> findByNome(String nome);

	List<Disciplina> findByNomeIn(List<String> nomes);
	
	boolean existsByNome(String nome);

}
