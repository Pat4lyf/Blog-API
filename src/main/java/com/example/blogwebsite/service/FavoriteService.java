package com.example.blogwebsite.service;

import com.example.blogwebsite.entities.Favorites;
import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.User;

import java.util.List;

public interface FavoriteService {
    Favorites getByPostAndUser(Post post, User user);

    Favorites saveFavorite(Favorites favourite);

    List<Favorites> findAllByUser(User user);

    void deleteFavorite(Favorites favorite);
}
