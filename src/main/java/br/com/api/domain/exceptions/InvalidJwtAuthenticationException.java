package br.com.api.domain.exceptions;


import lombok.AllArgsConstructor;

import javax.security.sasl.AuthenticationException;
import java.io.Serial;
import java.io.Serializable;


@AllArgsConstructor
public class InvalidJwtAuthenticationException extends AuthenticationException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidJwtAuthenticationException(String ex){
        super(ex);
    }
}
