package br.com.api.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
import java.io.Serializable;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtTokenException extends RuntimeException implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
