package br.com.api.domain.data.vo.v1.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCredentialsVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

}
