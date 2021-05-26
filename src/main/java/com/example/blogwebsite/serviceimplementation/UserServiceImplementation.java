package com.example.blogwebsite.serviceimplementation;

import com.example.blogwebsite.dto.ResponseDTO;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.repositories.UserRepository;
import com.example.blogwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;


    public ResponseDTO addUser(User user) {

        ResponseDTO response = new ResponseDTO();

        try {
            List<String> domains = Arrays.asList("gmail.com", "yahoo.com", "hotmail.com");
            String[] email = user.getEmailAddress().split("@");
            if (!domains.contains(email[1])) {
                throw new Exception("Invalid email");
            }
            Optional<User> userDb = userRepository.getUserByEmailAddress(user.getEmailAddress());


            try {
                if (userDb.isPresent()) {
                    throw new Exception("This email is already registered");
                }

                User savedUser = userRepository.save(user);
                response.setData(savedUser);
                response.setMessage("Registration successful");
                response.setStatus(true);
                return response;

            } catch (Exception e) {
                response.setMessage(e.getMessage());
                response.setStatus(false);
                return response;

            }
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(false);
            return response;
        }

    }


    /**
     * Method to log in a user
     *
     * @param user the user to be logged in
     * @return the response
     */
    public ResponseDTO logInUser(User user) {
        Optional<User> userDb = userRepository.getUserByEmailAddressAndPassword
                (user.getEmailAddress(), user.getPassword());
        ResponseDTO response = new ResponseDTO();

        if (userDb.isPresent()) {
            response.setStatus(true);
            response.setData(userDb.get());
            response.setMessage("LogIn successful");
            return response;
        }
        response.setMessage("Invalid email or password");
        return response;

    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

}