package ucsal.oferta.professor.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ucsal.oferta.professor.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, UUID>, JpaSpecificationExecutor<Professor> {

	@Query("""
			    SELECT p
			    FROM Professor p
			    WHERE p.id = :id
			      AND p.user.enabled = true
			      AND p.user.accountNonLocked = true
			""")
	Optional<Professor> findActiveAndNotBlockedById(@Param("id") UUID id);

}
