package com.prod.Springboot.service;

import com.prod.Springboot.entry.User;
import com.prod.Springboot.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class UserServices {
    @Autowired
    private UserRepo userServices;

    private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

//    private static final Logger logger= LoggerFactory.getLogger(UserServices.class);

    public void saveNewUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userServices.save(user);
        } catch (Exception e) {
            log.error("Duplicate username is: {}",user.getUsername(),e);
            throw new RuntimeException(e);
        }
    }
    //To create object in Mongodb
    public void saveUser(User user){
       userServices.save(user);
    }

    //To get objects from Mongodb
    public List<User> getAll(){
        return userServices.findAll();
    }

    //To get an object based on its id
    public Optional<User> getById(ObjectId id){
        return userServices.findById(String.valueOf(id));
    }

    //To remove an object from Mongodb
    public void remove(ObjectId id){
        userServices.deleteById(String.valueOf(id));
    }

    public User findByUserName(String uN){
        return userServices.findByusername(uN);
    }
//    public User findBypassword(String pass){
//        return userServices.findBypassword(pass);
//    }
    public void saveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userServices.save(user);
    }
}
