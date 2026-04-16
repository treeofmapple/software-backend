package ucsal.oferta.logic.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class LoginAttemptComponent {

	@Value("${cache.chances.login.max-attempts:5}")
	private int MAX_ATTEMPTS;

	@Value("${cache.name.login-attempt}")
	private String cacheLoginAttemptName;

	private final CacheManager cacheManager;
	private Cache attemptsCache;

	public LoginAttemptComponent(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@PostConstruct
	public void init() {
		this.attemptsCache = cacheManager.getCache(cacheLoginAttemptName);
		if (this.attemptsCache == null) {
			throw new IllegalStateException("Cache 'login-attempt' not found. Please check your cache configuration.");
		}
	}

	public void loginSucceeded(String key) {
		attemptsCache.evict(key);
	}

	public void loginFailed(String key) {
		Integer attempts = attemptsCache.get(key, Integer.class);
		if (attempts == null) {
			attempts = 0;
		}
		int newAttempts = attempts + 1;
		attemptsCache.put(key, newAttempts);
	}

	public void isLoginBlocked(String key) {
		Integer attempts = attemptsCache.get(key, Integer.class);
		if (attempts != null && attempts >= MAX_ATTEMPTS) {
			log.warn("Blocked login attempt for user: {}", key);
			throw new LockedException("User account is locked due to too many failed login attempts.");
		}
	}

}
