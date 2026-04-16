package ucsal.oferta.logic.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.security.component.TokenComponent;
import ucsal.oferta.security.model.User;

@Service
@RequiredArgsConstructor
public class LogoutUtils implements LogoutHandler {

	private final TokenComponent tokenComponent;
	private final SecurityUtils securityUtils;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		if (authentication != null && authentication.getPrincipal() instanceof User) {
			var user = (User) authentication.getPrincipal();
			tokenComponent.revokeUserAllTokens(user);
			securityUtils.invalidateUserSession();
		}
	}
}
