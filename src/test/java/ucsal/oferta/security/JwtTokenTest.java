package ucsal.oferta.security;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Claims;
import ucsal.oferta.interfaces.ContainerSecurityTest;
import ucsal.oferta.logic.security.JwtService;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.model.User;

@SpringBootTest
@ContainerSecurityTest
@DisplayName("Jwt Token Test")
public class JwtTokenTest {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private User user;

	@BeforeEach
	void setup() {
		user = new User();
		user.setId(UUID.randomUUID());
		user.setNickname("testuser");
		user.setEmail("testuser@example.com");
		user.setPassword(passwordEncoder.encode("secret123"));
		user.setRole(Role.ADMIN);
		user.setEnabled(true);
		user.setAccountNonLocked(true);
	}

	@Test
	@Order(1)
	@DisplayName("Generate a valid JWT token for user")
	void shouldGenerateValidJwtToken() {
		String token = jwtService.generateToken(user);

		Assertions.assertThat(token).isNotNull();
		Assertions.assertThat(token).isNotEmpty();

		String usernameFromToken = jwtService.extractUsername(token);

		Assertions.assertThat(usernameFromToken).as("Token subject should match user email").isEqualTo(user.getEmail());
	}

	@Test
	@Order(2)
	@DisplayName("Need to include correct roles claim in token")
	void tokenShouldContainRolesClaim() {
		String token = jwtService.generateToken(user);

		Claims claims = ReflectionTestUtils.invokeMethod(jwtService, "extractAllClaims", token);

		Assertions.assertThat(claims).isNotNull();

		@SuppressWarnings("unchecked")
		List<String> roles = claims.get("roles", List.class);

		Assertions.assertThat(roles).containsExactlyInAnyOrder("ROLE_ADMIN", "admin:read", "admin:update",
				"admin:delete", "admin:create", "user:read", "user:update", "user:delete", "user:create");
	}

	@Test
	@Order(3)
	@DisplayName("Need to successfully validate a correct token")
	void shouldValidateCorrectToken() {
		String token = jwtService.generateToken(user);
		boolean isValid = jwtService.isTokenValid(token, user);

		Assertions.assertThat(isValid).isTrue();
	}

	@Test
	@Order(4)
	@DisplayName("Should invalidate token when validated against a different user")
	void shouldInvalidateTokenForDifferentUser() {
		String token = jwtService.generateToken(user);
		User differentUser = new User();
		differentUser.setId(UUID.randomUUID());
		differentUser.setNickname("differentUser");
		differentUser.setEmail("different@example.com");
		differentUser.setPassword(passwordEncoder.encode("secret123"));
		differentUser.setRole(Role.ALUNO);
		differentUser.setEnabled(true);
		differentUser.setAccountNonLocked(true);
		boolean isValid = jwtService.isTokenValid(token, differentUser);
		Assertions.assertThat(isValid).isFalse();
	}

	@Test
	@Order(5)
	@DisplayName("Should return false for an expired token")
	void shouldInvalidateExpiredToken() {
		String expiredToken = ReflectionTestUtils.invokeMethod(jwtService, "buildToken", new HashMap<String, Object>(),
				user, -1000L);

		Assertions.assertThat(expiredToken).isNotNull();
		Assertions.assertThatThrownBy(() -> jwtService.isTokenValid(expiredToken, user))
				.isInstanceOf(io.jsonwebtoken.ExpiredJwtException.class);
	}

	@Test
	@Order(6)
	@DisplayName("Should generate a valid refresh token")
	void shouldGenerateValidRefreshToken() {
		String refreshToken = jwtService.generateRefreshToken(user);

		Assertions.assertThat(refreshToken).isNotNull().isNotBlank();

		String extractedUsername = jwtService.extractUsername(refreshToken);
		Assertions.assertThat(extractedUsername).isEqualTo(user.getEmail());

		boolean isValid = jwtService.isTokenValid(refreshToken, user);
		Assertions.assertThat(isValid).isTrue();
	}

}
