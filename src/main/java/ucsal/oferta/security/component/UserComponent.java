package ucsal.oferta.security.component;

import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ucsal.oferta.exception.sql.DataViolationException;
import ucsal.oferta.exception.sql.NotFoundException;
import ucsal.oferta.security.model.User;
import ucsal.oferta.security.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserComponent {

	private final UserRepository repository;

	public User findByActiveUser(UUID userId) {
		return repository.findActiveUserById(userId)
				.orElseThrow(() -> new NotFoundException(String.format("The user id: %s was not found", userId)));
	}

	public User findById(UUID userId) {
		return repository.findById(userId)
				.orElseThrow(() -> new NotFoundException(String.format("The user id: %s was not found", userId)));
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException(String.format("The user email: %s was not found", email)));
	}

	public void ensureNickanmeAndEmailAreUnique(String nickname, String email) {
		if (repository.existsByNickname(nickname)) {
			throw new DataViolationException("Nickname is already taken: " + nickname);
		}

		if (repository.existsByEmail(email)) {
			throw new DataViolationException("Email is already in use: " + email);
		}
	}

	public void checkIfEmailAlreadyUsed(User currentUser, String newEmail) {
		if (repository.existsByEmailAndIdNot(newEmail, currentUser.getId())) {
			throw new DataViolationException("Email is already in use by another account: " + newEmail);
		}
	}

	public void checkIfNicknameAlreadyUsed(User currentUser, String newUsername) {
		if (repository.existsByNicknameAndIdNot(newUsername, currentUser.getId())) {
			throw new DataViolationException("Nickname is already taken by another account: " + newUsername);
		}
	}

}
