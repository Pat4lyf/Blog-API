package com.example.blogwebsite.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "favorites")
@Data
public class Favorites implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
