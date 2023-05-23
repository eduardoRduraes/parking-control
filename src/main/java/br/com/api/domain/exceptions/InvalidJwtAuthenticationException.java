package br.com.api.domain.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.sasl.AuthenticationException;
import java.io.Serial;
import java.io.Serializable;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidJwtAuthenticationException(String ex){
        super(ex);
    }
}
