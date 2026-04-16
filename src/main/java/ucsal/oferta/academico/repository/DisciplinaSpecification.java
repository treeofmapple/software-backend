package ucsal.oferta.academico.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Predicate;
import ucsal.oferta.academico.model.Disciplina;

@Component
public class DisciplinaSpecification {

	public static Specification<Disciplina> findByCriteria(String name, String codigo, Integer cargaHorariaMin,
			Integer cargaHorariaMax) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (name != null && !name.trim().isEmpty()) {
				predicates.add(
						criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
			}

			if (codigo != null && !codigo.trim().isEmpty()) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("codigo")),
						"%" + codigo.toLowerCase() + "%"));
			}

			if (cargaHorariaMin != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cargaHoraria"), cargaHorariaMin));
			}

			if (cargaHorariaMax != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cargaHoraria"), cargaHorariaMax));
			}

			if (predicates.isEmpty()) {
				return criteriaBuilder.conjunction();
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
