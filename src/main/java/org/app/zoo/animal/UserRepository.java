package org.app.zoo.repository;


import org.app.zoo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.swagger.annotations.ApiModel;
import java.util.Optional;

@Repository
@ApiModel(description = "User interface to interact with the DB")
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNombreUsuario(String NombreUsuario);
}