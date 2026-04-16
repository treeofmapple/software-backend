package ucsal.oferta.security.service;

import java.time.Duration;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.exception.security.InvalidTokenException;
import ucsal.oferta.logic.cache.LoginAttemptComponent;
import ucsal.oferta.logic.security.JwtService;
import ucsal.oferta.logic.security.SecurityUtils;
import ucsal.oferta.security.component.TokenComponent;
import ucsal.oferta.security.component.UserComponent;
import ucsal.oferta.security.dto.authentication.AuthenticationRequest;
import ucsal.oferta.security.dto.authentication.AuthenticationResponse;
import ucsal.oferta.security.dto.authentication.RegisterRequest;
import ucsal.oferta.security.mapper.UserMapper;
import ucsal.oferta.security.model.User;
import ucsal.oferta.security.repository.TokenRepository;
import ucsal.oferta.security.repository.UserRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {

	@Value("${security.tokens.refresh-token}")
	private Duration refreshExpiration;

	private final AuthenticationManager authManager;
	private final PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final UserComponent userComponent;

	private final TokenRepository tokenRepository;
	private final TokenComponent tokenComponent;
	private final JwtService jwtService;
	
	private final LoginAttemptComponent loginTryService;

	private final SecurityUtils securityUtils;

	@Transactional
	public AuthenticationResponse register(RegisterRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		var userIp = securityUtils.getRequestingClientIp();
		log.info("IP: {}, is creating user.", userIp);

		userComponent.ensureNickanmeAndEmailAreUnique(request.nickname(), request.email());

		if (!Objects.equals(request.password(), request.confirmPassword())) {
			throw new BadCredentialsException("Passwords not matches");
		}

		var user = userMapper.build(request);
		user.setPassword(passwordEncoder.encode(request.password()));

		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(savedUser);
		var refreshToken = jwtService.generateRefreshToken(savedUser);

		tokenComponent.saveUserToken(savedUser, refreshToken);
		log.info("IP: {}, registered user Email: {}", userIp, savedUser.getEmail());
		return userMapper.toResponse(jwtToken, refreshToken);
	}

	@Transactional
	public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		String identifier = request.email();
		var userIp = securityUtils.getRequestingClientIp();
		log.info("IP: {}, is authenticating user: {}", userIp, identifier);

		loginTryService.isLoginBlocked(identifier);

		try {
			var auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(identifier, request.password()));

			var user = (User) auth.getPrincipal();
			var jwtToken = jwtService.generateToken(user);
			var refreshToken = jwtService.generateRefreshToken(user);

			tokenComponent.saveUserToken(user, refreshToken);

			loginTryService.loginSucceeded(identifier);
			
			log.info("IP: {}, authenticated with the user: {}", userIp, user.getUsername());
			return userMapper.toResponse(jwtToken, refreshToken);
		} catch (BadCredentialsException e) {
			log.warn("Failed login attempt for user: {}", request.email());
			loginTryService.loginFailed(identifier);
			throw e;
		}
	}

	@Transactional
	public AuthenticationResponse refreshToken(String providedRefreshToken, HttpServletRequest request,
			HttpServletResponse response) {

		String tokenToProcess = tokenComponent.resolveToken(providedRefreshToken, request);

	    if (tokenToProcess == null) {
	        throw new InvalidTokenException("No refresh token provided.");
	    }
		
		var storedToken = tokenRepository.findByToken(tokenToProcess)
	            .orElseThrow(() -> new InvalidTokenException("Refresh token not found in database."));

		if (storedToken.isExpired() || storedToken.isRevoked()) {
			throw new InvalidTokenException("Refresh token is expired.");
		}

		var user = storedToken.getUser();

		if (!user.isEnabled() || !user.isAccountNonLocked()) {
			log.warn("User banned: {}, tried to authenticate on: {}", user.getUsername(),
					securityUtils.getRequestingClientIp());
			throw new InvalidTokenException("User account is disabled or locked.");
		}

		var newAccessToken = jwtService.generateToken(user);
		var newRefreshToken = jwtService.generateRefreshToken(user);

		storedToken.setRevoked(true);
		storedToken.setExpired(true);

		tokenRepository.save(storedToken);
		tokenComponent.saveUserToken(user, newRefreshToken);

		return userMapper.toResponse(newAccessToken, newRefreshToken);
	}

}
