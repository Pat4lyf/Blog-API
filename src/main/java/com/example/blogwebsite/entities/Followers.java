package com.example.blogwebsite.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "followers")
@Data
public class Followers {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long followerId;

    @Column(nullable = false)
    private Long followeeId;

}
