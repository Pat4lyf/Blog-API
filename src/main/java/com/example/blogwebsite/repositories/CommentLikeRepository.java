package com.example.blogwebsite.repositories;

import com.example.blogwebsite.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikes, Long> {
    Optional<CommentLikes> findByCommentAndUser(Comment comment, User user);

}
