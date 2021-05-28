package com.example.blogwebsite.repositories;

import com.example.blogwebsite.entities.Comment;
import com.example.blogwebsite.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentId(Long id);

    List<Comment> findByPost(Post post);
}
