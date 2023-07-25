package br.com.api.domain.services;

import br.com.api.domain.data.vo.v1.security.AccountCredentialsVO;
import br.com.api.domain.data.vo.v1.security.TokenVO;
import br.com.api.domain.repositories.UserRepository;
import br.com.api.infra.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    final AuthenticationManager authenticationManager;

    final JwtTokenProvider jwtTokenProvider;

    final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO data){
        try{
            var username = data.getUsername();
            var password = data.getPassword();
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

            var user = userRepository.findByUsername(username);


            var tokenResponse = new TokenVO();

            if(user != null){
                tokenResponse = jwtTokenProvider.createAccessToken(username, user.Roles());
            }else{
                throw new UsernameNotFoundException("Username "+ username+" not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        }catch (Exception e){
            throw new BadCredentialsException("Invalid username/password supplied! or "+ e.getMessage());
        }
    }
}
