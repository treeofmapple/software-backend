package ucsal.oferta.professor.repository.Specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import ucsal.oferta.professor.model.Professor;
import ucsal.oferta.security.model.User;

@Component
public class ProfessorSpecification {

	public static Specification<Professor> findByCriteria(String searchTerm) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			Join<Professor, User> userJoin = root.join("user");

			predicates.add(cb.isTrue(userJoin.get("enabled")));
			predicates.add(cb.isTrue(userJoin.get("accountNonLocked")));

			if (searchTerm != null && !searchTerm.isBlank()) {
				String pattern = "%" + searchTerm.trim().toLowerCase() + "%";

				Predicate nomePredicate = cb.like(cb.lower(root.get("nome")), pattern);
				Predicate nicknamePredicate = cb.like(cb.lower(userJoin.get("nickname")), pattern);
				Predicate emailPredicate = cb.like(cb.lower(userJoin.get("email")), pattern);

				predicates.add(cb.or(nomePredicate, nicknamePredicate, emailPredicate));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

}
