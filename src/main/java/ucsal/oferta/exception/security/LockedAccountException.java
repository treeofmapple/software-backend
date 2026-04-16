package ucsal.oferta.exception.security;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import ucsal.oferta.exception.global.GlobalRuntimeException;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class LockedAccountException extends GlobalRuntimeException {

	public LockedAccountException(String message) {
		super(message, HttpStatus.LOCKED);
	}
	
	public LockedAccountException(String message, Throwable cause) {
		super(message, cause, HttpStatus.LOCKED);
	}
	
}
