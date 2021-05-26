package com.example.blogwebsite.repositories;

import com.example.blogwebsite.entities.Favorites;
import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {
    Favorites findByPostAndUser(Post post, User user);

    List<Favorites> findAllByUser(User user);
}
