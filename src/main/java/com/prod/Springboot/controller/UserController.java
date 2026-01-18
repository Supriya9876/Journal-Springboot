package com.prod.Springboot.controller;

import com.prod.Springboot.api.response.weatherResponse;
import com.prod.Springboot.entity.User;
import com.prod.Springboot.repository.UserRepo;
import com.prod.Springboot.service.UserServices;
import com.prod.Springboot.service.journalEntryService;
import com.prod.Springboot.service.weatherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Component
public class UserController {
    @Autowired
    public UserServices uServices;

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public journalEntryService jService;

    @Autowired
    public weatherServices weatherServices;




//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
//        String uName= authentication.getName();
//        User currUser=uServices.findByUserName(uName);
//        return new ResponseEntity<>(currUser, HttpStatus.OK);
//    }


    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User upUser){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String uName= authentication.getName();
        User currUser=uServices.findByUserName(uName);
        currUser.setUsername(upUser.getUsername());
        currUser.setPassword(upUser.getPassword());
        uServices.saveNewUser(currUser);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByusername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK,HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        weatherResponse weatherResponse= weatherServices.getWeather("Mumbai");
        String greeting="";
        if(weatherResponse!=null){
            greeting=" Weather feels like : "+ weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+authentication.getName() + greeting, HttpStatus.OK);
    }

}
