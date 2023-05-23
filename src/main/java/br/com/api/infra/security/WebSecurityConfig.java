package br.com.api.infra.security;

import br.com.api.domain.exceptions.DelegatedAuthorizationEntryPoint;
import br.com.api.domain.exceptions.FilterChainExceptionHandler;
import br.com.api.infra.security.jwt.JwtConfigurer;
import br.com.api.infra.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    final JwtTokenProvider jwtTokenProvider;
    final DelegatedAuthorizationEntryPoint delegatedAuthorizationEntryPoint;
    final FilterChainExceptionHandler filterChainExceptionHandler;


    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider, DelegatedAuthorizationEntryPoint delegatedAuthorizationEntryPoint, FilterChainExceptionHandler filterChainExceptionHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.delegatedAuthorizationEntryPoint = delegatedAuthorizationEntryPoint;
        this.filterChainExceptionHandler = filterChainExceptionHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder;
    }

    @Bean
    AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtConfigurer jwtConfigurer = new JwtConfigurer(jwtTokenProvider);
        return http
                .httpBasic().disable()
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                            auth.requestMatchers(HttpMethod.DELETE, "/park-spot/**").hasRole("USER");
                            auth.requestMatchers(HttpMethod.POST, "/park-spot/**").authenticated();
                            auth.requestMatchers(HttpMethod.PUT, "/park-spot/**").hasRole("ADMIN");
                            auth.requestMatchers(HttpMethod.GET, "/park-spot/**").authenticated();
                        }
                )
                .cors()
                .and()
                .addFilterBefore(filterChainExceptionHandler, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(delegatedAuthorizationEntryPoint)
                .and()
                .apply(jwtConfigurer)
                .and()
                .build();
    }
}