package com.example.blogwebsite.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "likes")
public class PostLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;


    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
