package ucsal.oferta.professor.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeRequest;
import ucsal.oferta.professor.dto.disponibilidade.DisponibilidadeUpdate;
import ucsal.oferta.professor.dto.internal.HorarioResponse;
import ucsal.oferta.professor.model.HorarioAula;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HorarioAulaMapper {

	@Mapping(target = "dia", expression = "java(Dia.from(request.dia()))")
	HorarioAula build(DisponibilidadeRequest request);

	@Mapping(target = "codigo", expression = "java(java.util.UUID.randomUUID().toString())")
	@Mapping(target = "dia", expression = "java(Dia.from(request.dia()))")
	HorarioAula build(DisponibilidadeUpdate request);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "dia", expression = "java(Dia.from(request.dia()))")
	void update(@MappingTarget HorarioAula horarioAula, DisponibilidadeUpdate request);

	HorarioResponse toResponse(HorarioAula horario);

}
