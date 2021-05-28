package com.example.blogwebsite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    private User user;


    @OneToMany
    @JsonIgnore
    private List<Comment> listOfComments = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<PostLikes> postLikes = new ArrayList<>();

}
