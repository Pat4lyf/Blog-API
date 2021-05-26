package com.example.blogwebsite.controllers;

import com.example.blogwebsite.dto.ResponseDTO;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user){
        ResponseDTO res = userService.addUser(user);

        if(res.isStatus())
            return new ResponseEntity<>(user, HttpStatus.OK);
        else{
            if(res.getMessage().equals("This email is already registered")){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
    }

    @GetMapping
    public ResponseEntity<List> getAllUsers(@RequestParam(value = "page", defaultValue = "1") Long page,
                                            @RequestParam(value = "limit", defaultValue = "2") int Limit) {
        List<User> listOfUsers = userService.findAllUsers();

        return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        Optional<User> optionalUser = userService.getUserById(userId);

        return optionalUser
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }


    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> logInUser(@Valid @RequestBody User user) {
        ResponseDTO response = userService.logInUser(user);

        if (response.isStatus()) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>( response, HttpStatus.NOT_ACCEPTABLE);
    }

}