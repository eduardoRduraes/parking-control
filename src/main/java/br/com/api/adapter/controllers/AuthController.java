package br.com.api.adapter.controllers;

import br.com.api.domain.data.vo.v1.security.AccountCredentialsVO;
import br.com.api.domain.data.vo.v1.security.CreateAccountCredentialsVO;
import br.com.api.domain.models.UserModel;
import br.com.api.domain.services.AuthService;
import br.com.api.domain.services.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    final AuthService authService;
    final UserService userService;
    public AuthController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }


    @SuppressWarnings("rawtypes")
    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data){
        if(checkIfParamsIsNotNull(data)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }
        var token = this.authService.signin(data);

        if(token == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        }

        return token;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody CreateAccountCredentialsVO data){
        var user = new UserModel();
        BeanUtils.copyProperties(data, user);
        this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private static boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null || data.getPassword().isBlank();
    }
    private static boolean checkIfParamsIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }

}
