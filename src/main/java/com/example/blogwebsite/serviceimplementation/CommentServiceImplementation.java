package com.example.blogwebsite.serviceimplementation;

import com.example.blogwebsite.entities.Comment;
import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.repositories.CommentRepository;
import com.example.blogwebsite.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImplementation(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findByCommentId(id);
    }

    @Override
    public List<Comment> getAllCommentsInPost(Post post) {
        return commentRepository.findByPost(post);
    }
}
