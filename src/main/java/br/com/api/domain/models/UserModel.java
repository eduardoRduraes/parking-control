package br.com.api.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class UserModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, name="user-name")
    private String userName;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "tb-users-roles", joinColumns = @JoinColumn(name = "user-id"), inverseJoinColumns = @JoinColumn(name = "role-id"))
    private List<RoleModel> roles;
}
