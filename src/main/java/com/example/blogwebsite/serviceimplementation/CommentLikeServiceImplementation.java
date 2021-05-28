package com.example.blogwebsite.serviceimplementation;

import com.example.blogwebsite.entities.Comment;
import com.example.blogwebsite.entities.CommentLikes;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.repositories.CommentLikeRepository;
import com.example.blogwebsite.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeServiceImplementation implements CommentLikeService {

    @Autowired
    CommentLikeRepository commentLikeRepository;


    @Override
    public void likeComment(CommentLikes like) {
        commentLikeRepository.save(like);
    }

    @Override
    public void deleteCommentLike(CommentLikes like) {
        commentLikeRepository.delete(like);
    }

    @Override
    public Optional<CommentLikes> findCommentLike(Comment comment, User user) {
        return commentLikeRepository.findByCommentAndUser(comment, user);

    }
}
