package br.com.api.domain.repositories;

import br.com.api.domain.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    @Query(value = "SELECT u FROM UserModel u WHERE u.username =:username")
    UserModel findByUsername(@Param("username") String username);
}
