package ucsal.oferta.professor.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import ucsal.oferta.academico.model.Disciplina;
import ucsal.oferta.professor.dto.interesse.InteresseResponse;
import ucsal.oferta.professor.dto.interesse.PageInteresseResponse;
import ucsal.oferta.professor.model.InteresseDisciplina;
import ucsal.oferta.professor.model.Professor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InteresseMapper {

	@Mapping(target = "id", ignore = true)
	InteresseDisciplina build(Professor professor, Set<Disciplina> disciplinas);

	InteresseResponse toResponse(InteresseDisciplina disciplina);

	@Mapping(target = "id", ignore = true)
	InteresseDisciplina update(@MappingTarget InteresseDisciplina disciplina, Professor professor,
			Set<Disciplina> disciplinas);

	List<InteresseResponse> toResponseList(List<InteresseDisciplina> disciplina);

	default PageInteresseResponse toResponse(Page<InteresseDisciplina> page) {
		if (page == null) {
			return null;
		}
		List<InteresseResponse> content = toResponseList(page.getContent());
		return new PageInteresseResponse(content, page.getNumber(), page.getSize(), page.getTotalPages());
	}

}
