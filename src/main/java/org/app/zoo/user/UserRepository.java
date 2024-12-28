package org.app.zoo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;

@Repository
@Schema(description = "User interface to interact with the DB")
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndEmailAndRoleId(String username, String email, int roleId);

    Optional<User> findByEmail(String email);

    Optional<User> findByResetToken(String resetToken);
}