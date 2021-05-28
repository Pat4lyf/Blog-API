package com.example.blogwebsite.service;

import com.example.blogwebsite.entities.User;

import java.util.List;

public interface FollowerService {
    String followUser(Long followeeId, User follower);
    List<User> getFollowersOfUser(User user);
    List<User> getUsersFollowedByUser(User user);

}
