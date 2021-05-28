package com.example.blogwebsite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity(name = "users")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    @Email
    private String emailAddress;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "int default 0")
    private int isDelete;


    @JsonIgnore
    @Column(name = "removeDate")
    private String removeDate;


    @JsonIgnore
    @Column(nullable = false, columnDefinition = "int default 0")
    private int personDeactivated;

}
