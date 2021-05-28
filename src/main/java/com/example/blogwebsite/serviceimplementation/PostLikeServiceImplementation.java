package com.example.blogwebsite.serviceimplementation;

import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.PostLikes;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.repositories.PostLikeRepository;
import com.example.blogwebsite.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikeServiceImplementation implements PostLikeService {

    @Autowired
    PostLikeRepository postLikeRepository;

    @Override
    public void likePost(PostLikes like) {
        postLikeRepository.save(like);
    }

    @Override
    public void deletePostLike(PostLikes like) {
        postLikeRepository.delete(like);
    }

    @Override
    public Optional<PostLikes> findPostLike(Post post, User user) {
        return postLikeRepository.findByPostAndUser(post, user);
    }
}
