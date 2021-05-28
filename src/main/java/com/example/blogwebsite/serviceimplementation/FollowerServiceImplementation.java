package com.example.blogwebsite.serviceimplementation;

import com.example.blogwebsite.entities.Followers;
import com.example.blogwebsite.entities.User;
import com.example.blogwebsite.repositories.FollowerRepository;
import com.example.blogwebsite.repositories.UserRepository;
import com.example.blogwebsite.service.FollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerServiceImplementation implements FollowerService {
    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @Autowired
    public FollowerServiceImplementation(FollowerRepository followerRepository, UserRepository userRepository) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String followUser(Long followeeId, User follower) {
        String response = "You are unable to make a connection";
        try {
                List<Followers> listOfFollowers = followerRepository.findByFollowerIdAndAndFolloweeId(follower.getUserId(), followeeId);

                if(listOfFollowers.size() == 0){
                    Followers newFollower = new Followers();
                    newFollower.setFolloweeId(followeeId);
                    newFollower.setFollowerId(follower.getUserId());
                    followerRepository.save(newFollower);
                    response = "You have made a new connection";
                }else{
                    followerRepository.deleteById(listOfFollowers.get(0).getId());
                    response = "You have unfollowed a connection";
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public List<User> getFollowersOfUser(User followee) {
        List<User> listOfFollowers = new ArrayList<>();
        try {
                List<Followers> list = followerRepository.findAllByFolloweeId(followee.getUserId());
                list.forEach(each->{
                    User user1 = new User();
                    User follower = userRepository.findById(each.getFollowerId()).get();
                    user1.setUserId(follower.getUserId());
                    user1.setEmailAddress(follower.getEmailAddress());
                    user1.setPassword(follower.getPassword());
                    listOfFollowers.add(user1);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfFollowers;
    }

    @Override
    public List<User> getUsersFollowedByUser(User follower) {
        List<User> listOfFollowees = new ArrayList<>();
        try {
                List<Followers> data = followerRepository.findAllByFollowerId(follower.getUserId());
                data.forEach(each->{
                    User user = new User();
                    User followee = userRepository.findById(each.getFolloweeId()).get();
                    user.setUserId(followee.getUserId());
                    user.setEmailAddress(followee.getEmailAddress());
                    user.setPassword(followee.getPassword());
                    listOfFollowees.add(user);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  listOfFollowees;
    }
}
