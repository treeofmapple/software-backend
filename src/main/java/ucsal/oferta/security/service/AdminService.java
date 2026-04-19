package ucsal.oferta.security.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.exception.security.AccessDeniedException;
import ucsal.oferta.logic.security.SecurityUtils;
import ucsal.oferta.security.component.TokenComponent;
import ucsal.oferta.security.component.UserComponent;
import ucsal.oferta.security.dto.admin.AdminPasswordUpdateRequest;
import ucsal.oferta.security.dto.admin.AdminRegisterRequest;
import ucsal.oferta.security.dto.admin.AdminRoleUpdate;
import ucsal.oferta.security.dto.admin.AdminUpdateRequest;
import ucsal.oferta.security.dto.admin.AdminUserBan;
import ucsal.oferta.security.dto.admin.AdminUserResponse;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.mapper.AdminMapper;
import ucsal.oferta.security.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class AdminService {

	@Value("${application.size.page:20}")
	private int PAGE_SIZE;

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;
	private final UserComponent userComponent;
	
	private final AdminMapper adminMapper;

	private final TokenComponent tokenComponent;
	
	private final SecurityUtils securityUtils;

	@Transactional
	public AdminUserResponse registerAdmin(AdminRegisterRequest request) {
		var userIp = securityUtils.getRequestingClientIp();
		log.info("IP: {}, admin is creating user.", userIp);

		var systemUser = securityUtils.getUser();
		if (systemUser.getRole() != Role.ADMIN) {
			throw new AccessDeniedException(String.format("User not allowed to register a new Admin: %s", userIp));
		}

		userComponent.ensureNickanmeAndEmailAreUnique(request.nickname(), request.email());

		if (!request.password().equals(request.confirmPassword())) {
			throw new BadCredentialsException("Passwords not matches");
		}

		var user = adminMapper.build(request);
		user.setPassword(passwordEncoder.encode(request.password()));

		var savedUser = userRepository.save(user);

		log.info("Admin registered an user: | Email: {}", savedUser.getEmail());
		return adminMapper.toResponse(savedUser);
	}

	@Transactional
	public AdminUserResponse adminUpdateUserRole(AdminRoleUpdate request) {
		var userIp = securityUtils.getRequestingClientIp();
		var user = userComponent.findById(request.userId());

		log.info("IP: {}, admin is updating user: {} role from {} to {}.", userIp, user.getEmail(), user.getRole(),
				request.role());

		adminMapper.update(user, request.role());
		userRepository.save(user);

		log.info("Admin changed user: {} role.", user.getEmail());
		return adminMapper.toResponse(user);
	}

	@Transactional
	public AdminUserResponse adminBanUser(AdminUserBan request) {
		var userIp = securityUtils.getRequestingClientIp();
		var admin = securityUtils.getUser();
		var user = userComponent.findById(request.userId());

		if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
			throw new BadCredentialsException("Password not matches");
		}

		log.info("IP: {}, admin: {}, banned user: {}.", userIp, admin.getEmail(), user.getEmail());

		user.setAccountNonLocked(false);
		user.setEnabled(false);
		return adminMapper.toResponse(user);
	}

	@Transactional
	public AdminUserResponse adminUnbanUser(AdminUserBan request) {
		var userIp = securityUtils.getRequestingClientIp();
		var admin = securityUtils.getUser();
		var user = userComponent.findById(request.userId());

		if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
			throw new BadCredentialsException("Password not matches");
		}

		log.info("IP: {}, admin: {}, unbanned user: {}.", userIp, admin.getEmail(), user.getEmail());

		user.setAccountNonLocked(true);
		user.setEnabled(true);
		return adminMapper.toResponse(user);
	}

	@Transactional
	public AdminUserResponse adminUpdateInfo(AdminUpdateRequest request) {
		var userIp = securityUtils.getRequestingClientIp();
		var user = securityUtils.getUser();
		log.info("IP: {}, admin is updating its information: {}.", userIp, user.getEmail());

		if (request.nickname() != null && !request.nickname().equals(user.getNickname())) {
			userComponent.checkIfNicknameAlreadyUsed(user, request.nickname());
		}

		if (request.email() != null && !request.email().equals(user.getEmail())) {
			userComponent.checkIfEmailAlreadyUsed(user, request.email());
		}

		adminMapper.update(user, request);
		userRepository.save(user);
		return adminMapper.toResponse(user);
	}

	// add question
	
	@Transactional
	public AdminUserResponse adminUpdateUserPassword(AdminPasswordUpdateRequest request) {
		var userIp = securityUtils.getRequestingClientIp();
		var user = userComponent.findById(request.userId());
		log.info("IP: {}, admin is updating user: {} password.", userIp, user.getEmail());

		if (user.getPassword().equals(request.password())) {
			throw new BadCredentialsException("Admin password is not right.");
		}

		if (!Objects.equals(request.newPassword(), request.confirmPassword())) {
			throw new BadCredentialsException("Passwords are not the same.");
		}

		user.setPassword(passwordEncoder.encode(request.newPassword()));
		userRepository.save(user);
		tokenComponent.revokeUserAllTokens(user);

		log.info("Admin changed user: {} password.", user.getEmail());
		return adminMapper.toResponse(user);
	}

}
