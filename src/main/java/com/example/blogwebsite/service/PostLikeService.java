package com.example.blogwebsite.service;

import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.PostLikes;
import com.example.blogwebsite.entities.User;

import java.util.Optional;

public interface PostLikeService {
    void likePost(PostLikes like);
    void deleteLike(PostLikes like);
    Optional<PostLikes> findPostLike(Post post, User user);

}
