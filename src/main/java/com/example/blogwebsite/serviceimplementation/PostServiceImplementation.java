package com.example.blogwebsite.serviceimplementation;

import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.repositories.CommentRepository;
import com.example.blogwebsite.repositories.PostLikeRepository;
import com.example.blogwebsite.repositories.PostRepository;
import com.example.blogwebsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository likeRepository;

    @Autowired
    CommentRepository commentRepository;

    /**
     * Method to save a post made by a user to the database
     *  @param user the user making the post
     * @param post the post made
     * @return
     */
    public void addPost(User user, Post post) {
        post.setUser(user);
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPostsByUser(User user) {
        return postRepository.findAllByUser(user);
    }

    /**
     * Method to save an edited post to the database
     *
     * @param post
     */
    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    /**
     * Method to delete a post from the database
     *
     * @param post the post to be deleted
     */
    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    /**
     * Method to get a post by the postId
     *
     * @param id the id of the post
     * @return the post
     */
    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findByPostId(id);
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }
}
