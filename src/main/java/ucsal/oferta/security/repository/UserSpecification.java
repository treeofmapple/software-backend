package ucsal.oferta.security.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.model.User;

@Component
public class UserSpecification {

	private static void enforceActiveStatus(Root<User> root, CriteriaBuilder criteriaBuilder,
			List<Predicate> predicates) {
		predicates.add(criteriaBuilder.isTrue(root.get("accountNonLocked")));
		predicates.add(criteriaBuilder.isTrue(root.get("enabled")));
	}

	private static String sanitizeForLike(String input) {
		if (input == null) {
			return null;
		}
		return input.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
	}

	public static Specification<User> findByCriteria(String nickname, String email, Role roles) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			enforceActiveStatus(root, criteriaBuilder, predicates);

			if (nickname != null && !nickname.trim().isEmpty()) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nickname")),
						"%" + sanitizeForLike(nickname.trim().toLowerCase()) + "%"));
			}

			if (email != null && !email.trim().isEmpty()) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
						"%" + sanitizeForLike(email.trim().toLowerCase()) + "%"));
			}

			if (roles != null) {
				predicates.add(criteriaBuilder.equal(root.get("role"), roles));
			}

			if (predicates.isEmpty()) {
				return criteriaBuilder.conjunction();
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
