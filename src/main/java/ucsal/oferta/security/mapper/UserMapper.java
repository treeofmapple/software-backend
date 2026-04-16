package ucsal.oferta.security.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;
import org.springframework.data.domain.Page;

import ucsal.oferta.aluno.mapper.AlunoMapper;
import ucsal.oferta.aluno.model.Aluno;
import ucsal.oferta.global.Pessoa;
import ucsal.oferta.professor.mapper.ProfessorMapper;
import ucsal.oferta.professor.model.Professor;
import ucsal.oferta.security.dto.authentication.AuthenticationResponse;
import ucsal.oferta.security.dto.authentication.RegisterRequest;
import ucsal.oferta.security.dto.external.AlunoUtilsResponse;
import ucsal.oferta.security.dto.external.ProfessorUtilsResponse;
import ucsal.oferta.security.dto.user.PageUserResponse;
import ucsal.oferta.security.dto.user.PersonalUserResponse;
import ucsal.oferta.security.dto.user.UserResponse;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { ProfessorMapper.class,
		AlunoMapper.class })
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "pessoa", ignore = true)
	User build(RegisterRequest request);

	@Mapping(target = ".", source = "user")
	@Mapping(target = ".", source = "user.pessoa")
	@Mapping(target = "details", source = "user.pessoa")
	PersonalUserResponse toPersonalResponse(User user, Boolean showAlert);

	UserResponse toResponse(User user);

	AuthenticationResponse toResponse(String accessToken, String refreshToken);

	ProfessorUtilsResponse toResponse(Professor professor);

	AlunoUtilsResponse toResponse(Aluno aluno);

	@SubclassMapping(source = Professor.class, target = ProfessorUtilsResponse.class)
	@SubclassMapping(source = Aluno.class, target = AlunoUtilsResponse.class)
	Object map(Pessoa pessoa);

	List<UserResponse> toResponseList(List<User> users);

	default PageUserResponse toResponse(Page<User> page) {
		if (page == null) {
			return null;
		}
		List<UserResponse> content = toResponseList(page.getContent());
		return new PageUserResponse(content, page.getNumber(), page.getSize(), page.getTotalPages());
	}

	@ObjectFactory
	default Pessoa createPessoa(User user) {
		if (user != null && user.getRole() == Role.PROFESSOR) {
			return new Professor();
		}
		return new Aluno();
	}

	@AfterMapping
	default void setStudentDefaults(@MappingTarget User user, RegisterRequest request) {
		user.setRole(Role.ALUNO);
		user.setPessoa(new Aluno());
	}

	default PersonalUserResponse toPersonalResponse(User user) {
		return toPersonalResponse(user, false);
	}
}
