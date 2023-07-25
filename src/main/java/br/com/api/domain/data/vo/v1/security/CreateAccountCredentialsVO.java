package br.com.api.domain.data.vo.v1.security;

import br.com.api.domain.models.PermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountCredentialsVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String fullName;
    private String username;
    private String password;

}
