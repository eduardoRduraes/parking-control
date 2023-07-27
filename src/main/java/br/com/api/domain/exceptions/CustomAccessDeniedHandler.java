package br.com.api.domain.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

@ResponseStatus(HttpStatus.FORBIDDEN)
@ControllerAdvice
public class CustomAccessDeniedHandler implements Serializable, AccessDeniedHandler {

    @Serial
    private final static long serialVersionUID = 1L;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        throw new ExceptionAccessDeniedError(accessDeniedException.getClass().getSimpleName()+" - "+accessDeniedException.getMessage());
    }
}
