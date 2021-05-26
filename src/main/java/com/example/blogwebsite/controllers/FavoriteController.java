package com.example.blogwebsite.controllers;

import com.example.blogwebsite.entities.Favorites;
import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.service.FavoriteService;
import com.example.blogwebsite.service.PostService;
import com.example.blogwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog/stars")
public class FavoriteController {
    PostService postService;
    FavoriteService favoriteService;
    UserService userService;

    @Autowired
    public FavoriteController(PostService postService, FavoriteService favoriteService, UserService userService) {
        this.postService = postService;
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    //Star a post or add to favourite list
    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<Favorites> addPostToFavorite(@PathVariable(name = "postId") Long postId,
                                               @PathVariable(name = "userId") Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            Optional<Post> post = postService.getPostById(postId);
            if (post.isPresent()) {
                Favorites favorite = favoriteService.getByPostAndUser(post.get(), user.get());
                if (favorite == null) {
                    Favorites favourite1 = new Favorites();
                    favourite1.setUser(user.get());
                    favourite1.setPost(post.get());
                   Favorites favorite2 = favoriteService.saveFavorite(favourite1);
                    return new ResponseEntity<>(favorite2, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    //get all starred post by user
    @GetMapping("/{userId}")
    public List<Post> getAllFavouritePosts(@PathVariable(name = "userId") Long userId) {
        List<Post> listOfPosts = new ArrayList<>();

        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            System.out.println("OOOOOOOO "+userId);
                List<Favorites> listOfFavorites = favoriteService.findAllByUser(user.get());
            System.out.println("uuuuuuu " + listOfFavorites.size());
//                List<Post> listOfPosts = new ArrayList<>();
                for (Favorites favorite : listOfFavorites)
                    listOfPosts.add(favorite.getPost());
            System.out.println("iiiiiiii " + listOfPosts.size());

//                return listOfPosts;
        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return listOfPosts;
    }

    // delete post from favourite list
    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<?> deleteStarredPost (@PathVariable(name = "userId") Long userId,
                                                @PathVariable(name = "postId") Long postId) {
        Optional<User> user = userService.getUserById(userId);
        Optional<Post> post = postService.getPostById(postId);

        if (user.isPresent() && post.isPresent()) {

                Favorites favorite = favoriteService.getByPostAndUser(post.get(), user.get());

                if (favorite != null) {
                    favoriteService.deleteFavorite(favorite);
                    return new ResponseEntity<>(HttpStatus.OK);
                }

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

