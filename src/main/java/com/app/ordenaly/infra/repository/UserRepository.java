package com.app.ordenaly.infra.repository;

import com.app.ordenaly.infra.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
  Optional<User> findByName(String username);
  Optional<User> findByEmail(String email);

}

// Optional,


