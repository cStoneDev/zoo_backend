package org.app.zoo.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    // Note that the second argument is now an Int cause de id of token is integer
  List<Token> findAllByExpiredFalseAndRevokedFalseAndUser_Id(Long userId);

  Optional<Token> findByToken(String token);
}


