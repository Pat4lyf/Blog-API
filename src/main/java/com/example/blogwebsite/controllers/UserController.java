package com.example.blogwebsite.controllers;

import com.example.blogwebsite.dto.ResponseDTO;
import com.example.blogwebsite.dto.UserDTO;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    //to register a user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user){
        UserDTO res = userService.addUser(user);

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

    //to get all registered users
    @GetMapping
    public ResponseEntity<List> getAllUsers(@RequestParam(value = "page", defaultValue = "1") Long page,
                                            @RequestParam(value = "limit", defaultValue = "2") int Limit) {
        List<User> listOfUsers = userService.findAllUsers();

        return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }


    //to get a registered user by the id
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        Optional<User> optionalUser = userService.getUserById(userId);

        return optionalUser
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


    //to log in a user
    @PostMapping("/login")
    public ResponseEntity<UserDTO> logInUser(@Valid @RequestBody User user, HttpServletRequest request) {
        UserDTO response = userService.logInUser(user);

        if (response.isStatus()) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", response.getData());
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>( response, HttpStatus.NOT_ACCEPTABLE);
    }



    //to delete a registered user
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteUser(@PathVariable Long id, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("loggedUser");

        //http server response
        ResponseDTO response = new ResponseDTO();
        if(user == null) {
            response.setStatusCode(401);
            response.setMessage("user not logged in!!!");
            return response;
        }

        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isPresent() && optionalUser.get().getUserId().equals(user.getUserId())) {

            if(userService.deleteUser(id, user)){
            response.setStatusCode(204);
            response.setMessage("account deletion pending");
        }

        } else{
            response.setStatusCode(401);
            response.setMessage("not your account");
        }
        return response;
    }



    //to reverse the delete request of a user
    @PostMapping("/reverseDelete/{id}")
    public ResponseEntity<?> reverseAccountDeletion(@PathVariable Long id,  HttpSession httpSession){
        User user = (User) httpSession.getAttribute("loggedUser");

        //http server response
        ResponseDTO response = new ResponseDTO();
        if(user == null) {
            response.setStatusCode(401);
            response.setMessage("user not logged in!!!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String message = userService.reverseDeleteActionUserAccount(user, id);
        if(message.equals("successfully reversed")){
            response.setStatusCode(202);
            response.setMessage(message);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }

        else{
            response.setMessage(message);

            if(message.equals("user not authorized")){
                response.setStatusCode(401);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            else if(message.equals("user not found")){
                response.setStatusCode(404);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }


            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}