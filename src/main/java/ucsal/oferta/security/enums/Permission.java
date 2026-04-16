package ucsal.oferta.security.enums;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
	
	USER_READ("user:read"),
	USER_UPDATE("user:update"),
	USER_DELETE("user:delete"),
	USER_CREATE("user:create"),

	PROFESSOR_READ("professor:read"),
	PROFESSOR_UPDATE("professor:update"),
	PROFESSOR_DELETE("professor:delete"),
	PROFESSOR_CREATE("professor:create"),

	ADMIN_READ("admin:read"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_DELETE("admin:delete"),
	ADMIN_CREATE("admin:create"),
	;

	@Getter
	private final String permission;

	protected static final Set<Permission> USER_PERMISSIONS = Set.of(
            USER_READ, USER_UPDATE, USER_DELETE, USER_CREATE
    );

	protected static final Set<Permission> PROFESSOR_PERMISSIONS = Set.of(
            PROFESSOR_READ, PROFESSOR_UPDATE, PROFESSOR_DELETE, PROFESSOR_CREATE
    );

    protected static final Set<Permission> ADMIN_PERMISSIONS = Set.of(
            ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE
    );

}
