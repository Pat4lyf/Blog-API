package com.example.blogwebsite.service;

import com.example.blogwebsite.dto.ResponseDTO;
import com.example.blogwebsite.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseDTO addUser(User user);

    ResponseDTO logInUser(User user);

    List<User> findAllUsers();

    Optional<User> getUserById(Long id);
}

