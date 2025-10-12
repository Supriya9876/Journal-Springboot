package com.prod.Springboot.controller;

import com.prod.Springboot.entry.User;
import com.prod.Springboot.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class publicController {
    @GetMapping("/helth")
    public String check(){
        return "Ok";
    }

    @Autowired
    private UserServices uServices;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        uServices.saveNewUser(user);
    }
}
