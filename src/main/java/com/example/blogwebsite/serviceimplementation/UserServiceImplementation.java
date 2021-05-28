package com.example.blogwebsite.serviceimplementation;

import com.example.blogwebsite.dto.UserDTO;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.repositories.UserRepository;
import com.example.blogwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;


    public UserDTO addUser(User user) {

        UserDTO response = new UserDTO();

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
    public UserDTO logInUser(User user) {
        Optional<User> userDb = userRepository.getUserByEmailAddressAndPassword
                (user.getEmailAddress(), user.getPassword());
        UserDTO response = new UserDTO();

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

    @Override
    public void deactivatedUserScheduler() {
        List<User> users = userRepository.findAllByPersonDeactivated(1);
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        System.out.println("scheduler working");
        users.forEach(user -> {
            String presentDate = DateFor.format(date);
            String deleteDate = user.getRemoveDate();
            int actionDelete = presentDate.compareTo(deleteDate);
            if(actionDelete > 0 || actionDelete == 0) {
                System.out.println(user.getEmailAddress() +" is finally deleted");
                user.setIsDelete(1);
                userRepository.save(user);
            }
        });
    }


    @Override
    public boolean deleteUser(Long userId, User user) {
        boolean status = false;
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            User user1 = userRepository.getUserByEmailAddress(user.getEmailAddress()).get();
            if(user1.getUserId() == userId){
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, 1);
                String presentDate = DateFor.format(c.getTime());
                user1.setPersonDeactivated(1);
                user1.setRemoveDate(presentDate);
                userRepository.save(user1);
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    @Override
    public String reverseDeleteActionUserAccount(User user, Long userId) {
        String status = "Server Error";
        try {
            User user1 = userRepository.getUserByEmailAddress(user.getEmailAddress()).get();
            if(user1.getUserId() != userId) status = "user not authorized";
            else{
                if(user1.getIsDelete() == 0){
                    user1.setPersonDeactivated(0);
                    userRepository.save(user1);
                    status = "successfully reversed";
                }else{
                    status = "user not found";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}