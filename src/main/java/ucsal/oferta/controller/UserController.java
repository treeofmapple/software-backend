package ucsal.oferta.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.security.dto.user.PageUserResponse;
import ucsal.oferta.security.dto.user.PasswordUpdateRequest;
import ucsal.oferta.security.dto.user.PersonalUserResponse;
import ucsal.oferta.security.dto.user.UserResponse;
import ucsal.oferta.security.enums.Role;
import ucsal.oferta.security.service.UserService;
import ucsal.oferta.security.service.utils.UserSortOption;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

	private final UserService userService;

	@GetMapping(value = "/me")
	@ResponseStatus(HttpStatus.OK)
	public PersonalUserResponse getCurrentUser() {
		return userService.getCurrentUserAuthenticated();
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserResponse searchUserById(@PathVariable(value = "id") UUID userId) {
		return userService.searchUserById(userId);
	}

	@GetMapping(value = "/search")
	@ResponseStatus(HttpStatus.OK)
	public PageUserResponse searchUserByParams(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false) String nickname, @RequestParam(required = false) String email,
			@RequestParam(required = false, value = "role") Role roles,
			@RequestParam(required = false, value = "sort") UserSortOption sortParam) {
		return userService.searchUserByParams(page, nickname, email, roles, sortParam);
	}

	@PutMapping(value = "/password")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void changeUserPassword(@RequestBody @Valid PasswordUpdateRequest request,
			HttpServletResponse httpResponse) {
		userService.changeUserPassword(request, httpResponse);
	}

	/*
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeMyAccount(@RequestBody @Valid DeleteAccountRequest request, HttpServletResponse httpResponse) {
		userService.removeMyAccount(request, httpResponse);
	}

    */
}
