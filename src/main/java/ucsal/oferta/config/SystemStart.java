package ucsal.oferta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ucsal.oferta.global.SystemActionContext;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.model.User;
import ucsal.oferta.security.repository.UserRepository;

@Log4j2
@Component
@RequiredArgsConstructor
public class SystemStart implements CommandLineRunner {

	@Value("${settings.security.user:admin}")
	private String username;

	@Value("${settings.security.email:admin@admin.com}")
	private String email;

	@Value("${settings.security.password:admin}")
	private String password;

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final SystemActionContext systemAction;

	@Override
	public void run(String... args) throws Exception {
		generateAdminUser();
	}

	@EventListener(ContextRefreshedEvent.class)
	public void generateAdminUser() {
		systemAction.runAsSystem(() -> {
			if (!repository.existsByNickname(username)) {
				User user = new User();
				user.setNickname(username);
				user.setEmail(email);
				user.setPassword(passwordEncoder.encode(password));
				user.setRole(Role.ADMIN);
				try {
					repository.save(user);
					log.info(">>>> Default ADMIN user created: {}", username);
				} catch (DataIntegrityViolationException e) {
					log.warn(">>>> Default ADMIN user '{}' was created by another instance concurrently. Skipping.",
							username);
				}
			} else {
				log.info(">>>> Default ADMIN user '{}' already exists. Skipping creation.", username);
			}
			return null;
		});
	}
}
