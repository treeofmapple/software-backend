package ucsal.oferta.exception.security;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import ucsal.oferta.exception.global.GlobalRuntimeException;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class ActiveSessionException extends GlobalRuntimeException {
	public ActiveSessionException(String message) {
		super(message, HttpStatus.CONFLICT);
	}

	public ActiveSessionException(String message, Throwable cause) {
		super(message, cause, HttpStatus.CONFLICT);
	}
}
