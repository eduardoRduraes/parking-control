package br.com.api.domain.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class UserAlreadyExistsException extends RuntimeException implements Serializable {

        @Serial
        private final static long serialVersionUID = 1L;

    public UserAlreadyExistsException(String message) {
            super(message);
        }
}
