package br.com.api.domain.repositories;

import br.com.api.domain.models.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionModel, UUID> {

    PermissionModel findPermissionModelByDescription(String description);
}
