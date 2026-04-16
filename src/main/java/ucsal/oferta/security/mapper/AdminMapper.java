package ucsal.oferta.security.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import ucsal.oferta.aluno.model.Aluno;
import ucsal.oferta.global.Pessoa;
import ucsal.oferta.professor.model.Professor;
import ucsal.oferta.security.dto.admin.AdminRegisterRequest;
import ucsal.oferta.security.dto.admin.AdminUpdateRequest;
import ucsal.oferta.security.dto.admin.AdminUserResponse;
import ucsal.oferta.security.dto.admin.PageAdminUserResponse;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { UserMapper.class })
public interface AdminMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "pessoa", ignore = true)
	User build(AdminRegisterRequest request);

	@Mapping(target = ".", source = "user")
	@Mapping(target = ".", source = "user.pessoa")
	@Mapping(target = "details", source = "user.pessoa")
	AdminUserResponse toResponse(User user);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void update(@MappingTarget User user, AdminUpdateRequest request);

	@Mapping(target = "id", ignore = true)
	void update(Pessoa source, @MappingTarget Pessoa target);
	
	@AfterMapping
	default void afterBuild(@MappingTarget User user, AdminRegisterRequest request) {
		if (request.role() != null) {
			Role role = Role.checkValueOf(request.role().toUpperCase());
			user.setRole(role);

			switch (role) {
			case ADMIN -> user.setPessoa(null);
			case PROFESSOR -> user.setPessoa(new Professor());
			case ALUNO -> user.setPessoa(new Aluno());
			default -> throw new IllegalStateException("Unexpected role: " + role);
			}
		}
	}

	default void update(@MappingTarget User user, Role newRole) {
		if (user.getRole() == newRole) {
			return;
		}

		Pessoa oldPessoa = user.getPessoa();
		Pessoa newPessoa = switch (newRole) {
		case ADMIN -> null;
		case PROFESSOR -> new Professor();
		case ALUNO -> new Aluno();
		default -> throw new IllegalArgumentException("Unexpected value: " + newRole);
		};

		if (oldPessoa != null && newPessoa != null) {
			update(oldPessoa, newPessoa);
		}

		user.setPessoa(newPessoa);
		user.setRole(newRole);
	}

	List<AdminUserResponse> toResponseList(List<User> users);

	default PageAdminUserResponse toResponse(Page<User> page) {
		if (page == null) {
			return null;
		}
		List<AdminUserResponse> content = toResponseList(page.getContent());
		return new PageAdminUserResponse(content, page.getNumber(), page.getSize(), page.getTotalPages());
	}

}
