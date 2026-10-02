package com.airline.reservation.repository;

import com.airline.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Method for Spring Security to find a user by their username
    Optional<User> findByUsername(String username);
}