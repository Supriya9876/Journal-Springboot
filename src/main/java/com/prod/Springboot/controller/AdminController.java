package com.prod.Springboot.controller;

import com.prod.Springboot.cache.AppCache;
import com.prod.Springboot.entity.User;
import com.prod.Springboot.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUser(){
        List<User> users= userServices.getAll();
        return (users.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT):
        new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping("/create-admin")
    public void addAdmin(@RequestBody User user){
        userServices.saveNewAdmin(user);
    }


    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
