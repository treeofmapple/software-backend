package ucsal.oferta.exception.security;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import ucsal.oferta.exception.global.GlobalRuntimeException;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class AccessDeniedException extends GlobalRuntimeException {

	public AccessDeniedException(String message) {
		super(message, HttpStatus.UNAUTHORIZED);
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause, HttpStatus.UNAUTHORIZED);
	}

}
