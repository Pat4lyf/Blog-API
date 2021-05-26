package com.example.blogwebsite.serviceimplementation;

import com.example.blogwebsite.entities.Favorites;
import com.example.blogwebsite.entities.Post;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.repositories.FavoriteRepository;
import com.example.blogwebsite.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImplementation implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteServiceImplementation(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }


    @Override
    public Favorites getByPostAndUser(Post post, User user) {
        return favoriteRepository.findByPostAndUser(post, user);
    }

    @Override
    public Favorites saveFavorite(Favorites favourite) {
        return favoriteRepository.save(favourite);
    }

    @Override
    public List<Favorites> findAllByUser(User user) {
        return favoriteRepository.findAllByUser(user);
    }

    @Override
    public void deleteFavorite(Favorites favorite) {
        favoriteRepository.delete(favorite);
    }
}
