package br.com.api.domain.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthorizationEntryPoint implements AuthenticationEntryPoint {

//    final HandlerExceptionResolver resolve;
//
//    public DelegatedAuthorizationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolve) {
//        this.resolve = resolve;
//    }

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            log.error("Error: "+authException.getClass().getSimpleName()+" "+ authException.getMessage());
            throw new InvalidJwtAuthenticationException(authException.getClass().getSimpleName()+" - "+authException.getMessage());

    }
}
