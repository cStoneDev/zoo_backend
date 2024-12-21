package org.app.zoo.repository;

import java.util.List;

import org.app.zoo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.swagger.annotations.ApiModel;
import java.util.Optional;

@Repository
@ApiModel(description = "Role interface to interact with the DB")
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNombre(String nombre);
}