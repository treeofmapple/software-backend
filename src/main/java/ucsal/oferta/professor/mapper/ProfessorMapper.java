package ucsal.oferta.professor.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import ucsal.oferta.academico.model.Disciplina;
import ucsal.oferta.professor.dto.internal.DisciplinaDTO;
import ucsal.oferta.professor.dto.professor.PageProfessorResponse;
import ucsal.oferta.professor.dto.professor.ProfessorAlternativasResponse;
import ucsal.oferta.professor.dto.professor.ProfessorAlternativasUpdate;
import ucsal.oferta.professor.dto.professor.ProfessorRequest;
import ucsal.oferta.professor.dto.professor.ProfessorResponse;
import ucsal.oferta.professor.dto.professor.ProfessorUpdateRequest;
import ucsal.oferta.professor.model.Professor;
import ucsal.oferta.security.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { DisponibilidadeMapper.class,
		InteresseMapper.class })
public interface ProfessorMapper {

	default void build(@MappingTarget User user, ProfessorRequest request) {
		if (request == null) {
			return;
		}

		Professor professor;
		if (user.getPessoa() instanceof Professor p) {
			professor = p;
		} else {
			professor = new Professor();
			user.setPessoa(professor);
		}

		buildFrom(professor, request);
	}

	default void update(@MappingTarget User user, ProfessorUpdateRequest request) {
		if (user.getPessoa() instanceof Professor professor) {
			updateFrom(professor, request);
		}
	}

	default List<String> mergeAlternativas(List<String> current, List<String> incoming) {
		if (incoming == null) {
			return current;
		}
		if (current == null) {
			return incoming;
		}

		List<String> result = new ArrayList<>(current);
		for (String alt : incoming) {
			if (!result.contains(alt)) {
				result.add(alt);
			}
		}
		return result;
	}

	default void update(@MappingTarget User user, ProfessorAlternativasUpdate request) {
		if (user == null || request == null) {
			return;
		}

		if (user.getPessoa() instanceof Professor professor) {
			updateAlternativas(professor, request);
		}
	}

	@Mapping(target = "dataNascimento", source = "dataNascimento")
	void buildFrom(@MappingTarget Professor professor, ProfessorRequest request);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFrom(@MappingTarget Professor professor, ProfessorUpdateRequest request);

	@Mapping(target = "alternativas", expression = "java(mergeAlternativas(professor.getAlternativas(), request.alternativas()))")
	void updateAlternativas(@MappingTarget Professor professor, ProfessorAlternativasUpdate request);

	ProfessorResponse toResponse(Professor professor);

	DisciplinaDTO toResponse(Disciplina disciplina);

	@Mapping(source = "user.nickname", target = "nickname")
	@Mapping(source = "user.email", target = "email")
	ProfessorAlternativasResponse toAlternativasResponse(Professor professor);

	List<ProfessorResponse> toResponseList(List<Professor> disponibilidade);

	default PageProfessorResponse toResponse(Page<Professor> page) {
		if (page == null) {
			return null;
		}
		List<ProfessorResponse> content = toResponseList(page.getContent());
		return new PageProfessorResponse(content, page.getNumber(), page.getSize(), page.getTotalPages());
	}

}
