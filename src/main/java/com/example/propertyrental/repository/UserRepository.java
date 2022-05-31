package com.example.propertyrental.repository;

import com.example.propertyrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByUserName(String username);

    boolean existsByEmail(String email);
}
