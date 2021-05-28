package com.example.blogwebsite.service;

import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.User;

import java.util.List;
import java.util.Optional;


public interface PostService {
    void addPost(User user, Post post);

    List<Post> getAllPostsByUser(User user);

    void updatePost(Post post);

    void deletePost(Post post);

    Optional<Post> getPostById(Long id);

    List<Post> findAllPosts();


}
