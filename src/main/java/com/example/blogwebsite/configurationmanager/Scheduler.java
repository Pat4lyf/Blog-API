package com.example.blogwebsite.configurationmanager;

import com.example.blogwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Scheduler {
    @Autowired
    UserService userService;

    @Scheduled(fixedRate = 60000L)
    public void scheduleTaskWithFixedRate() {
        userService.deactivatedUserScheduler();
    }
}
