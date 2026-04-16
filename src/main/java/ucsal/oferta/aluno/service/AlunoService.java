package ucsal.oferta.aluno.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.aluno.dto.AlunoRequest;
import ucsal.oferta.aluno.dto.AlunoUpdateRequest;
import ucsal.oferta.aluno.mapper.AlunoMapper;
import ucsal.oferta.logic.cache.ProfileCacheComponent;
import ucsal.oferta.logic.security.SecurityUtils;
import ucsal.oferta.security.component.UserComponent;
import ucsal.oferta.security.dto.user.PersonalUserResponse;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.mapper.UserMapper;
import ucsal.oferta.security.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class AlunoService {

	private final UserRepository userRepository;
	private final AlunoMapper alunoMapper;
	private final UserMapper userMapper;
	private final ProfileCacheComponent profileCache;
	private final SecurityUtils securityUtils;
	private final UserComponent userComponent;

	@Transactional
	public PersonalUserResponse registerUserInformations(AlunoRequest request) {
		var userIp = securityUtils.getRequestingClientIp();
		var user = securityUtils.getUser();

		if (user.getRole() != Role.ALUNO) {
			throw new IllegalStateException("Only users with role Aluno can register aluno details.");
		}

		if (user.getPessoa() != null && user.getPessoa().isCadastroCompleto()) {
			throw new IllegalStateException("User profile is already registered. Please use the update endpoint.");
		}

		log.info("IP: {}, is registering aluno: {}, informations.", userIp, user.getEmail());

		alunoMapper.build(user, request);
		userRepository.save(user);
		profileCache.profileCompleted(user.getEmail());
		return userMapper.toPersonalResponse(user);
	}

	@Transactional
	public PersonalUserResponse editAlunoInformations(AlunoUpdateRequest request) {
		var userIp = securityUtils.getRequestingClientIp();
		var user = securityUtils.getUser();
		log.info("IP: {}, is editing user: {}", userIp, user.getEmail());

		if (request.nickname() != null && !request.nickname().equals(user.getNickname())) {
			userComponent.checkIfNicknameAlreadyUsed(user, request.nickname());
		}

		if (request.email() != null && !request.email().equals(user.getEmail())) {
			userComponent.checkIfEmailAlreadyUsed(user, request.email());
		}

		alunoMapper.update(user, request);
		var userEdited = userRepository.save(user);

		log.info("IP: {}, updated aluno profile: {}, to: {}", userIp, user.getEmail(), userEdited.getEmail());

		return userMapper.toPersonalResponse(userEdited);
	}

}
