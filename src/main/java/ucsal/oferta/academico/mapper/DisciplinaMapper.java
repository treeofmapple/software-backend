package ucsal.oferta.academico.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import ucsal.oferta.academico.dto.DisciplinaRequest;
import ucsal.oferta.academico.dto.DisciplinaResponse;
import ucsal.oferta.academico.dto.DisciplinaUpdate;
import ucsal.oferta.academico.dto.PageDisciplinaResponse;
import ucsal.oferta.academico.model.Disciplina;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DisciplinaMapper {

	@Mapping(target = "id", ignore = true)
	Disciplina build(DisciplinaRequest request);

	DisciplinaResponse toResponse(Disciplina disciplina);

	@Mapping(target = "id", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void update(@MappingTarget Disciplina disciplina, DisciplinaUpdate request);

	List<DisciplinaResponse> toResponseList(List<Disciplina> disciplina);

	default PageDisciplinaResponse toResponse(Page<Disciplina> page) {
		if (page == null) {
			return null;
		}
		List<DisciplinaResponse> content = toResponseList(page.getContent());
		return new PageDisciplinaResponse(content, page.getNumber(), page.getSize(), page.getTotalPages());
	}

}
