package ucsal.oferta.professor.repository.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Predicate;
import ucsal.oferta.professor.model.Disponibilidade;

@Component
public class DisponibilidadeSpecification {

	public static Specification<Disponibilidade> findByCriteria(String professorName, LocalTime inicio, LocalTime fim) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
            if (professorName != null && !professorName.isBlank()) {
                predicates.add(cb.like(
                    cb.lower(root.get("professor").get("nome")), 
                    "%" + professorName.toLowerCase() + "%"
                ));
            }

            if (inicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.join("horarioAulas").get("inicio"), inicio));
            }
            
            if (fim != null) {
                predicates.add(cb.lessThanOrEqualTo(root.join("horarioAulas").get("fim"), fim));
            }
            
			return cb.and(predicates.toArray(new Predicate[0])); 
		};
	}
	
}
