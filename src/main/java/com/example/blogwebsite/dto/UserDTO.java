package com.example.blogwebsite.dto;

import com.example.blogwebsite.entities.User;
import lombok.Data;

@Data
public class UserDTO {
    private String message;
    private User data;
    private boolean status;

}
