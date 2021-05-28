package com.example.blogwebsite.service;

import com.example.blogwebsite.entities.Comment;
import com.example.blogwebsite.entities.Post;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    void saveComment(Comment comment);

    Optional<Comment> getCommentById(Long id);

    List<Comment> getAllCommentsInPost(Post post);
}
