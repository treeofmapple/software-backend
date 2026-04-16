package ucsal.oferta.aluno.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

import ucsal.oferta.aluno.dto.AlunoRequest;
import ucsal.oferta.aluno.dto.AlunoResponse;
import ucsal.oferta.aluno.dto.AlunoUpdateRequest;
import ucsal.oferta.aluno.model.Aluno;
import ucsal.oferta.global.Pessoa;
import ucsal.oferta.security.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlunoMapper {

	void build(@MappingTarget User user, AlunoRequest request);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "pessoa", source = "request")
	void update(@MappingTarget User user, AlunoUpdateRequest request);

	AlunoResponse toResponse(Aluno aluno);

	@ObjectFactory
    default Pessoa createPessoa() {
        return new Aluno();
    }
}
