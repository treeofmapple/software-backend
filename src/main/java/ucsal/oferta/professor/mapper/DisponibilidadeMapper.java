package ucsal.oferta.professor.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeRequest;
import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeResponse;
import ucsal.oferta.professor.dto.disponibilidade.PageDisponibilidadeResponse;
import ucsal.oferta.professor.model.Disponibilidade;
import ucsal.oferta.professor.model.Professor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
		HorarioAulaMapper.class
})
public interface DisponibilidadeMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "horarioAulas", ignore = true)
	Disponibilidade build(DisponibilidadeRequest request, Professor professor);
	
	@Mapping(target = "nickname", source = "professor.nome")
	DisponibilidadeResponse toResponse(Disponibilidade disponibilidade);
	
    List<DisponibilidadeResponse> toResponseList(List<Disponibilidade> disponibilidade);

	default PageDisponibilidadeResponse toResponse(Page<Disponibilidade> page) {
		if (page == null) {
			return null;
		}
		List<DisponibilidadeResponse> content = toResponseList(page.getContent());
		return new PageDisponibilidadeResponse(content, page.getNumber(), page.getSize(), page.getTotalPages());
	}
	
}
