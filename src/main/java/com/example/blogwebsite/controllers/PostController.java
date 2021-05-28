package com.example.blogwebsite.controllers;

import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.PostLikes;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.service.FollowerService;
import com.example.blogwebsite.service.PostLikeService;
import com.example.blogwebsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog/posts")
public class PostController {

    private final FollowerService followerService;
    private final PostService postService;
    private final PostLikeService likeService;

    @Autowired
    public PostController(FollowerService followerService, PostService postService, PostLikeService likeService) {
        this.followerService = followerService;
        this.postService = postService;
        this.likeService = likeService;
    }

    //get all posts made
    @GetMapping()
    public ResponseEntity<List> getAllPosts(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");

        if (user != null) {
            List<Post> listOfPosts = postService.findAllPosts();
            return new ResponseEntity<>(listOfPosts, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    //create a post
    @PostMapping()
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user != null) {
            postService.addPost(user, post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    //get posts of connections
    @GetMapping("/followees")
    public ResponseEntity<List> getPostsOfConnections(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("loggedUser");

        List<User> listOfFollowees = followerService.getUsersFollowedByUser(user);
        if (listOfFollowees.size() != 0) {
            List<Post> listOfPosts = postService.findAllPosts();

            List<Post> listOfPostsOfConnections = new ArrayList<>();

            for (Post post: listOfPosts) {
                for (User user1: listOfFollowees) {
                    if (post.getUser().equals(user1))
                        listOfPostsOfConnections.add(post);
                }
            }
            return new ResponseEntity<>(listOfPostsOfConnections, HttpStatus.OK);
        }


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //like and unlike a post
    @PostMapping("/likes/{postId}")
    public ResponseEntity<PostLikes> likeAndUnlikePost(@Valid PostLikes postLike,
                                              @PathVariable Long postId,
                                              HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        Optional<Post> optionalPost = postService.getPostById(postId);

        if (user != null && optionalPost.isPresent()) {
            Optional<PostLikes> optionalPostLikes = likeService.findPostLike(optionalPost.get(), user);

            if (optionalPostLikes.isPresent()) {
                PostLikes postLikes = optionalPostLikes.get();

                if (postLikes.getUser().getUserId().equals(user.getUserId()))
                    likeService.deletePostLike(postLikes);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            postLike.setPost(optionalPost.get());
            postLike.setUser(user);

            likeService.likePost(postLike);
            return new ResponseEntity<>(postLike, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
