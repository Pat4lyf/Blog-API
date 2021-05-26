package com.example.blogwebsite.controllers;

import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.PostLikes;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.service.PostLikeService;
import com.example.blogwebsite.service.PostService;
import com.example.blogwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog/posts")
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final PostLikeService likeService;

    @Autowired
    public PostController(UserService userService, PostService postService, PostLikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List> getAllPosts(@PathVariable Long userId) {
        Optional<User> optional = userService.getUserById(userId);

        if (optional.isPresent()) {
            List<Post> listOfPosts = postService.findAllPosts();
            return new ResponseEntity<>(listOfPosts, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }



    @PostMapping("/{userId}")
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post, @PathVariable Long userId) {
        Optional<User> optional = userService.getUserById(userId);
        if (optional.isPresent()) {
            User savedUser = optional.get();
            postService.addPost(savedUser, post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/{userId}/likes/{postId}")
    public ResponseEntity<PostLikes> likePost(@Valid PostLikes postLike,
                                              @PathVariable Long userId,
                                              @PathVariable Long postId) {
        Optional<User> optionalUser = userService.getUserById(userId);
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (optionalUser.isPresent() && optionalPost.isPresent()) {
            Optional<PostLikes> optionalPostLikes = likeService.findPostLike(optionalPost.get(), optionalUser.get());

            if (optionalPostLikes.isPresent()) {
                PostLikes postLikes = optionalPostLikes.get();

                if (postLikes.getUser().getUserId().equals(optionalUser.get().getUserId()))
                    likeService.deleteLike(postLikes);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            postLike.setPost(optionalPost.get());
            postLike.setUser(optionalUser.get());

            likeService.likePost(postLike);
            return new ResponseEntity<>(postLike, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
