package com.example.blogwebsite.repositories;

import com.example.blogwebsite.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmailAddress(String emailAddress);

    Optional<User> getUserByEmailAddressAndPassword(String emailAddress, String password);
}
