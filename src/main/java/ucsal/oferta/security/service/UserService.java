package ucsal.oferta.security.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.global.Pessoa;
import ucsal.oferta.logic.cache.ProfileCacheComponent;
import ucsal.oferta.logic.security.SecurityUtils;
import ucsal.oferta.security.component.TokenComponent;
import ucsal.oferta.security.component.UserComponent;
import ucsal.oferta.security.dto.user.DeleteAccountRequest;
import ucsal.oferta.security.dto.user.PageUserResponse;
import ucsal.oferta.security.dto.user.PasswordUpdateRequest;
import ucsal.oferta.security.dto.user.PersonalUserResponse;
import ucsal.oferta.security.dto.user.UserResponse;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.mapper.UserMapper;
import ucsal.oferta.security.model.User;
import ucsal.oferta.security.repository.UserRepository;
import ucsal.oferta.security.repository.UserSpecification;
import ucsal.oferta.security.service.utils.UserSortOption;
import ucsal.oferta.security.service.utils.UserSortParameter;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {

	@Value("${application.size.page:10}")
	private int PAGE_SIZE;

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserSortParameter userSort;
	private final UserMapper userMapper;
	private final UserComponent userHelper;
	private final TokenComponent tokenComponent;
	private final ProfileCacheComponent profileCache;
	private final SecurityUtils securityUtils;

	// Return the user authenticated info

	@Transactional(readOnly = true)
	public PersonalUserResponse getCurrentUserAuthenticated() {
		var user = securityUtils.getUser();
		boolean isComplete = Optional.ofNullable(user.getPessoa()).map(Pessoa::isCadastroCompleto).orElse(false);
		if (isComplete) {
			return userMapper.toPersonalResponse(user);
		}
		return userMapper.toPersonalResponse(user, profileCache.shouldAlert(user.getEmail()));
	}

	@Transactional(readOnly = true)
	public UserResponse searchUserById(UUID query) {
		var user = userHelper.findByActiveUser(query);
		return userMapper.toResponse(user);
	}

	/**
	 * Search user by params.
	 * <p>
	 * Searches a list of users by page, using the selected parameters.
	 *
	 * @param page     the page number
	 * @param username the username to filter by (partial match)
	 * @param email    the email to filter by (partial match)
	 * @param age      the user's age (calculates birth date range)
	 * @param roles    the user role (professor, aluno)
	 * @return a {@link Page} containing the users that match the criteria
	 * @implNote This only active users.
	 */

	@Transactional(readOnly = true)
	public PageUserResponse searchUserByParams(int page, String username, String email, Role roles,
			UserSortOption sortParam) {
		var finalSort = userSort.selectUserSort(sortParam);
		Specification<User> spec = UserSpecification.findByCriteria(username, email, roles);
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, finalSort);
		Page<User> users = userRepository.findAll(spec, pageable);
		return userMapper.toResponse(users);
	}

	/*
	 * Allow the user to change its old password to a new one.
	 */

	@Transactional
	public void changeUserPassword(PasswordUpdateRequest request, HttpServletResponse httpResponse) {
		var user = securityUtils.getUser();
		var userIp = securityUtils.getRequestingClientIp();
		log.info("IP: {}, is changing password of user: {}", userIp, user.getUsername());

		if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
			throw new BadCredentialsException("Password not matches");
		}

		if (!request.newPassword().equals(request.confirmPassword())) {
			throw new BadCredentialsException("Passwords are not the same");
		}

		user.setPassword(passwordEncoder.encode(request.newPassword()));
		userRepository.save(user);

		tokenComponent.revokeUserAllTokens(user);
		log.info("IP: {}, changed successfully the password of user: {}", userIp, user.getEmail());
	}

	@Transactional
	public void removeMyAccount(DeleteAccountRequest request, HttpServletResponse httpResponse) {
		var user = securityUtils.getUser();
		log.info("IP: {}, is deleting user: {}", securityUtils.getRequestingClientIp(), user.getEmail());

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new BadCredentialsException("Incorrect password provided for account deletion.");
		}

		securityUtils.invalidateUserSession();
		tokenComponent.revokeUserAllTokens(user);

		// This will remove the user access and lock account
		// But it won't delete the user from the database.

		user.setAccountNonLocked(false);
		user.setEnabled(false);
		userRepository.save(user);

		// This deletes the user from the db
		// userRepository.deleteById(user.getId());

		tokenComponent.revokeUserAllTokens(user);
		log.info("The user {} has deleted their account", user.getEmail());
	}

}
