package ucsal.oferta.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ucsal.oferta.interfaces.ContainerSecurityTest;
import ucsal.oferta.security.enums.Permission;
import ucsal.oferta.security.enums.Role;

@ContainerSecurityTest
@DisplayName("Role and Permission Mapping")
public class RolePermissionTest {
	
	@Test
	@Order(1)
	@DisplayName("ADMIN role should contain correct admin and user permissions")
    void adminShouldHaveAdminAndUserPermissions() {
        Role adminRole = Role.ADMIN;

        Assertions.assertThat(adminRole.getPermissions()).contains(
                Permission.ADMIN_READ,
                Permission.ADMIN_CREATE,
                Permission.ADMIN_UPDATE,
                Permission.ADMIN_DELETE,
                Permission.USER_READ,
                Permission.USER_CREATE
        );

        Assertions.assertThat(adminRole.getPermissions()).doesNotContain(
                Permission.PROFESSOR_READ
        );

        Assertions.assertThat(adminRole.getAuthorities())
                .extracting(SimpleGrantedAuthority::getAuthority)
                .contains(
                        "ROLE_ADMIN",
                        "admin:read",
                        "user:read"
                );
    }

    @Test
	@Order(2)
    @DisplayName("PROFESSOR role should contain correct professor and user permissions")
    void professorShouldHaveProfessorAndUserPermissions() {
        Role profRole = Role.PROFESSOR;

        Assertions.assertThat(profRole.getPermissions()).contains(
                Permission.PROFESSOR_READ,
                Permission.USER_READ
        );
        
        Assertions.assertThat(profRole.getPermissions()).doesNotContain(
                Permission.ADMIN_READ
        );

        Assertions.assertThat(profRole.getAuthorities())
                .extracting(SimpleGrantedAuthority::getAuthority)
                .contains(
                        "ROLE_PROFESSOR",
                        "professor:read",
                        "user:read"
                );
    }

    @Test
	@Order(3)
    @DisplayName("USER role should contain only user permissions")
    void userShouldOnlyHaveUserPermissions() {
        Role userRole = Role.ALUNO;

        Assertions.assertThat(userRole.getPermissions()).contains(
                Permission.USER_READ
        );
        
        Assertions.assertThat(userRole.getPermissions()).doesNotContain(
                Permission.ADMIN_READ,
                Permission.PROFESSOR_READ
        );

        Assertions.assertThat(userRole.getAuthorities())
                .extracting(SimpleGrantedAuthority::getAuthority)
                .contains("ROLE_USER", "user:read");
    }
    
    @Test
	@Order(4)
    @DisplayName("ANONYMOUS role should have no permissions and only its role authority")
    void anonymousShouldHaveNoAuthorities() {
    	Role anonymousRole = Role.ANONYMOUS;

    	Assertions.assertThat(anonymousRole.getPermissions()).isEmpty();
    	Assertions.assertThat(anonymousRole.getAuthorities())
                .extracting(SimpleGrantedAuthority::getAuthority)
                .containsExactly("ROLE_ANONYMOUS");
    }
	
}
