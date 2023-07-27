package br.com.api.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name="permissions")
@NoArgsConstructor
public class PermissionModel implements GrantedAuthority, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String description;

    public PermissionModel(String description){
        this.description = description;
    }


    @Override
    public String getAuthority() {
        return this.description;
    }



}
