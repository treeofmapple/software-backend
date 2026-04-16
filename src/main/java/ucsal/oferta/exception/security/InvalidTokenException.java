package ucsal.oferta.exception.security;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import ucsal.oferta.exception.global.GlobalRuntimeException;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class InvalidTokenException extends GlobalRuntimeException {

	public InvalidTokenException(String message) {
		super(message, HttpStatus.FORBIDDEN);
	}

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause, HttpStatus.FORBIDDEN);
	}

}
