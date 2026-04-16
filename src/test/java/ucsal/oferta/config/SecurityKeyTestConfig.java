package ucsal.oferta.config;

import java.util.Base64;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Configuration
public class SecurityKeyTestConfig {

	@DynamicPropertySource
	static void registerSecurityKey(DynamicPropertyRegistry registry) throws Exception {
		registry.add("application.security.secret-key", () -> Base64.getEncoder().encodeToString(new byte[32]));
	}

}
