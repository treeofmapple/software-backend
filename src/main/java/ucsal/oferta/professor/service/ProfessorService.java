package ucsal.oferta.professor.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.logic.cache.ProfileCacheComponent;
import ucsal.oferta.logic.security.SecurityUtils;
import ucsal.oferta.professor.dto.professor.ProfessorAlternativasResponse;
import ucsal.oferta.professor.dto.professor.ProfessorAlternativasUpdate;
import ucsal.oferta.professor.dto.professor.ProfessorRequest;
import ucsal.oferta.professor.dto.professor.ProfessorUpdateRequest;
import ucsal.oferta.professor.mapper.ProfessorMapper;
import ucsal.oferta.professor.service.utils.ProfessorComponent;
import ucsal.oferta.security.component.UserComponent;
import ucsal.oferta.security.dto.user.PersonalUserResponse;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.mapper.UserMapper;
import ucsal.oferta.security.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProfessorService {

	@Value("${application.size.page:20}")
	private int PAGE_SIZE;

	private final ProfessorMapper professorMapper;
	private final ProfessorComponent professorUtils;

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final UserComponent userComponent;
	
	private final SecurityUtils securityUtils;

	private final ProfileCacheComponent profileCache;

	@Transactional(readOnly = true)
	public ProfessorAlternativasResponse findProfessorAlternativas(UUID query) {
		var response = professorUtils.findById(query);
		return professorMapper.toAlternativasResponse(response);
	}
	
	@Transactional
	public PersonalUserResponse registerProfessorInformations(ProfessorRequest request) {
		var userIp = securityUtils.getRequestingClientIp();
		var user = securityUtils.getUser();
		
		if (user.getRole() != Role.PROFESSOR) {
	        throw new IllegalStateException("Only users with role PROFESSOR can register professor details.");
	    }
		
		log.info("IP: {}, is registering professor: {}, informations.", userIp, user.getEmail());
		
		professorMapper.build(user, request);
		userRepository.save(user);
		profileCache.profileCompleted(user.getEmail());
		return userMapper.toPersonalResponse(user);
	}
	
	@Transactional
	public PersonalUserResponse updateProfessorQuestions(ProfessorAlternativasUpdate request) {
		var userIp = securityUtils.getRequestingClientIp();
		var user = securityUtils.getUser();
		
		log.info("IP: {}, is altering professor: {}, questions.", userIp, user.getEmail());
		
		professorMapper.update(user, request);
		userRepository.save(user);
		return userMapper.toPersonalResponse(user);
	}
	
	@Transactional
	public PersonalUserResponse updateProfessorInformations(ProfessorUpdateRequest request) {
		var userIp = securityUtils.getRequestingClientIp();
		var user = securityUtils.getUser();
		log.info("IP: {}, is registering professor: {}, informations.", userIp, user.getEmail());
		
		if (request.nickname() != null && !request.nickname().equals(user.getNickname())) {
			userComponent.checkIfNicknameAlreadyUsed(user, request.nickname());
		}

		if (request.email() != null && !request.email().equals(user.getEmail())) {
			userComponent.checkIfEmailAlreadyUsed(user, request.email());
		}
		
		professorMapper.update(user, request);
		var userEdited = userRepository.save(user);
		
		log.info("IP: {}, updated professor profile: {}, to: {}", userIp, user.getEmail(), userEdited.getEmail());

		return userMapper.toPersonalResponse(user);
	}
	
}
