package ucsal.oferta.exception.sql;

import org.springframework.http.HttpStatus;

import lombok.EqualsAndHashCode;
import ucsal.oferta.exception.global.GlobalRuntimeException;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends GlobalRuntimeException {
	
	public NotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause, HttpStatus.NOT_FOUND);
	}

}
