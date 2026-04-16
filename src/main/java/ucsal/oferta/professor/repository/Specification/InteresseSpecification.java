package ucsal.oferta.professor.repository.Specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Predicate;
import ucsal.oferta.professor.model.InteresseDisciplina;

@Component
public class InteresseSpecification {

	public static Specification<InteresseDisciplina> findByCriteria(String professor, String disciplina) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
            if (professor != null && !professor.isBlank()) {
                predicates.add(cb.like(
                    cb.lower(root.get("professor").get("nome")), 
                    "%" + professor.toLowerCase() + "%"
                ));
            }

            if (disciplina != null && !disciplina.isBlank()) {
                predicates.add(cb.like(
                    cb.lower(root.get("disciplina").get("nome")), 
                    "%" + disciplina.toLowerCase() + "%"
                ));
            }
			
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
