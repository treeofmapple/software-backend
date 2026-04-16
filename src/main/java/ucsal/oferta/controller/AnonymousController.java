package ucsal.oferta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.security.dto.authentication.AuthenticationRequest;
import ucsal.oferta.security.dto.authentication.AuthenticationResponse;
import ucsal.oferta.security.dto.authentication.RegisterRequest;
import ucsal.oferta.security.service.AuthenticationService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AnonymousController {

	private final AuthenticationService authService;

	@PostMapping(value = "/sign-up")
	@ResponseStatus(HttpStatus.CREATED)
	public AuthenticationResponse registerUser(@RequestBody @Valid RegisterRequest request,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		return authService.register(request, httpRequest, httpResponse);
	}

	@PostMapping(value = "/sign-in")
	@ResponseStatus(HttpStatus.OK)
	public AuthenticationResponse authenticateUser(@RequestBody @Valid AuthenticationRequest request,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		return authService.authenticate(request, httpRequest, httpResponse);
	}

	@PostMapping(value = "/refresh-token")
	@ResponseStatus(HttpStatus.OK)
	public AuthenticationResponse refreshToken(String refreshToken, HttpServletRequest request,
			HttpServletResponse response) {
		return authService.refreshToken(refreshToken, request, response);
	}

}

// DONE:
