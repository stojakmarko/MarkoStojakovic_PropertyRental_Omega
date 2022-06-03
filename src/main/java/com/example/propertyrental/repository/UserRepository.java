package com.example.propertyrental.repository;

import com.example.propertyrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserName(String username);

    @Query(nativeQuery = true, value = "SELECT * FROM USERS WHERE PASSWORD_RESET_TOKEN=:token")
    Optional<User> userWithPasswordResetToken(@Param("token") String token);

    boolean existsByEmail(String email);

    boolean existsByUserName(String username);

}
