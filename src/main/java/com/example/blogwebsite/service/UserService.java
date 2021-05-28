package com.example.blogwebsite.service;

import com.example.blogwebsite.dto.UserDTO;
import com.example.blogwebsite.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO addUser(User user);

    UserDTO logInUser(User user);

    List<User> findAllUsers();

    Optional<User> getUserById(Long id);

    void deactivatedUserScheduler();

    boolean deleteUser(Long userId, User user);

    String reverseDeleteActionUserAccount(User user, Long userId);
}

