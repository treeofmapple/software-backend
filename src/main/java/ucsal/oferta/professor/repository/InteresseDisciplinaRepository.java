package ucsal.oferta.professor.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ucsal.oferta.professor.model.InteresseDisciplina;

@Repository
public interface InteresseDisciplinaRepository
		extends JpaRepository<InteresseDisciplina, UUID>, JpaSpecificationExecutor<InteresseDisciplina> {

	List<InteresseDisciplina> findByProfessor_Id(UUID professorId);

	Page<InteresseDisciplina> findByDisciplina_Id(UUID disciplinaId, Pageable pageable);

	boolean existsByProfessor_IdAndDisciplina_Id(UUID professorId, UUID disciplinaId);
}
