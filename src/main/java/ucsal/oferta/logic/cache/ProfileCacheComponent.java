package ucsal.oferta.logic.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ProfileCacheComponent {

	@Value("${cache.chances.profile.max-attempts:3}")
	private int MAX_ATTEMPTS;

	@Value("${cache.name.profile-register}")
	private String cacheProfileRegisterAlert;

	private final CacheManager cacheManager;
	private Cache profileCache;

	public ProfileCacheComponent(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@PostConstruct
	public void init() {
		this.profileCache = cacheManager.getCache(cacheProfileRegisterAlert);
		if (this.profileCache == null) {
			throw new IllegalStateException(
					"Cache 'profile-register' not found. Please check your cache configuration.");
		}
	}

	public boolean shouldAlert(String key) {
		Integer attempts = profileCache.get(key, Integer.class);
		if (attempts == null) {
			attempts = 0;
		}
		if (attempts < MAX_ATTEMPTS) {
			int newAttempts = attempts + 1;
			profileCache.put(key, newAttempts);
			log.debug("Alerting user {} to complete profile. Attempt {}/{}", key, newAttempts, MAX_ATTEMPTS);
			return true;
		}
		return false;
	}

	public void profileCompleted(String key) {
		profileCache.evict(key);
		log.debug("Profile completion cache cleared for user: {}", key);
	}

}
