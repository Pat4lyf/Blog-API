package com.example.blogwebsite.controllers;

import com.example.blogwebsite.entities.Comment;
import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.service.CommentService;
import com.example.blogwebsite.service.PostService;
import com.example.blogwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog/comments")
public class CommentController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public CommentController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }



    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody Comment comment,
                                                 @PathVariable Long userId,
                                                 @PathVariable Long postId) {

        Optional<User> optionalUser = userService.getUserById(userId);
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (optionalUser.isPresent() && optionalPost.isPresent()) {
            User savedUser = optionalUser.get();
            comment.setUser(savedUser);

            optionalPost.ifPresent(comment::setPost);

            commentService.saveComment(comment);

//            comment.getPost().getListOfComments().add(comment.getCommentBody());

            optionalPost.get().getListOfComments().add(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
