package com.example.blogwebsite.repositories;

import com.example.blogwebsite.entities.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikes, Long> {

}
