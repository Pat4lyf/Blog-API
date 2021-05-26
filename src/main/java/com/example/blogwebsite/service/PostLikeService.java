package com.example.blogwebsite.service;

import com.example.blogwebsite.entities.PostLikes;

public interface PostLikeService {
    void likePost(PostLikes like);
    void deleteLike(PostLikes like);

}
