package org.app.zoo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;

@Repository
@Schema(description = "User interface to interact with the DB")
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}