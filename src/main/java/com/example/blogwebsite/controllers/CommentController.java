package com.example.blogwebsite.controllers;

import com.example.blogwebsite.entities.*;
import com.example.blogwebsite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog/comments")
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;


    @Autowired
    public CommentController(CommentLikeService commentLikeService, PostService postService, CommentService commentService) {
        this.commentLikeService = commentLikeService;
        this.postService = postService;
        this.commentService = commentService;
    }



    //to make a comment
    @PostMapping("/{postId}")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody Comment comment,
                                                 @PathVariable Long postId,
                                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (user != null && optionalPost.isPresent()) {
            comment.setUser(user);
            optionalPost.ifPresent(comment::setPost);
            commentService.saveComment(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //to get all comments in a post
    @GetMapping("/{postId}")
    public ResponseEntity<List> getAllCommentsInPost(@PathVariable Long postId, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (user != null && optionalPost.isPresent()) {
            List<Comment> listOfComments = commentService.getAllCommentsInPost(optionalPost.get());
            return new ResponseEntity<>(listOfComments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



    //like and unlike a comment
    @PostMapping("/commentlikes/{commentId}")
    public ResponseEntity<CommentLikes> likeAndUnlikeComment(@Valid CommentLikes commentLike,
                                                       @PathVariable Long commentId,
                                                       HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        Optional<Comment> optionalComment = commentService.getCommentById(commentId);

        if (user != null && optionalComment.isPresent()) {
            Optional<CommentLikes> optionalCommentLikes = commentLikeService.findCommentLike(optionalComment.get(), user);

            if (optionalCommentLikes.isPresent()) {
                CommentLikes commentLikes = optionalCommentLikes.get();

                if (commentLikes.getUser().getUserId().equals(user.getUserId()))
                    commentLikeService.deleteCommentLike(commentLikes);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            commentLike.setComment(optionalComment.get());
            commentLike.setUser(user);

            commentLikeService.likeComment(commentLike);
            return new ResponseEntity<>(commentLike, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
