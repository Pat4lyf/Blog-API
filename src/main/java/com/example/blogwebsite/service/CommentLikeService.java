package com.example.blogwebsite.service;

import com.example.blogwebsite.entities.*;

import java.util.Optional;

public interface CommentLikeService {
    void likeComment(CommentLikes like);
    void deleteCommentLike(CommentLikes like);
    Optional<CommentLikes> findCommentLike(Comment comment, User user);
}
