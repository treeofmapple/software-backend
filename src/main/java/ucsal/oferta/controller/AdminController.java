package ucsal.oferta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.security.dto.admin.AdminPasswordUpdateRequest;
import ucsal.oferta.security.dto.admin.AdminRegisterRequest;
import ucsal.oferta.security.dto.admin.AdminRoleUpdate;
import ucsal.oferta.security.dto.admin.AdminUpdateRequest;
import ucsal.oferta.security.dto.admin.AdminUserBan;
import ucsal.oferta.security.dto.admin.AdminUserResponse;
import ucsal.oferta.security.service.AdminService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {

	private final AdminService adminService;

	@PostMapping(value = "/register")
	@ResponseStatus(HttpStatus.CREATED)
	public AdminUserResponse registerAnUserAsAdmin(@RequestBody @Valid AdminRegisterRequest request) {
		return adminService.registerAdmin(request);
	}

	@PostMapping(value = "/role")
	@ResponseStatus(HttpStatus.OK)
	public AdminUserResponse adminUpdateUserRole(@RequestBody @Valid AdminRoleUpdate request) {
		return adminService.adminUpdateUserRole(request);
	}

	@PostMapping(value = "/ban")
	@ResponseStatus(HttpStatus.OK)
	public AdminUserResponse banAnUser(@RequestBody @Valid AdminUserBan request) {
		return adminService.adminBanUser(request);
	}

	@PostMapping(value = "/unban")
	@ResponseStatus(HttpStatus.OK)
	public AdminUserResponse unbanAnUser(@RequestBody @Valid AdminUserBan request) {
		return adminService.adminUnbanUser(request);
	}

	@PutMapping(value = "/change")
	@ResponseStatus(HttpStatus.OK)
	public AdminUserResponse updateAdminInfo(@RequestBody @Valid AdminUpdateRequest request) {
		return adminService.adminUpdateInfo(request);
	}

	@PutMapping(value = "/password")
	@ResponseStatus(HttpStatus.OK)
	public AdminUserResponse updateAnUserPassword(@RequestBody @Valid AdminPasswordUpdateRequest request) {
		return adminService.adminUpdateUserPassword(request);
	}

}
