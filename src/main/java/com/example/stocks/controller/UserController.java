package com.example.stocks.controller;

import com.example.stocks.domain.User;
import com.example.stocks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private static final String CLIENT_ID = "1016502741069-k249od2so3r40pn14815cjfb3c4nrdde.apps.googleusercontent.com";
    @Autowired
    UserRepository ur;
    @PostMapping("/adduser")
    User addUser(){
        User u =new User("sdgwd","wuegduwy","jhasjgahg@gmail.com");

        return  ur.save(u);
    }

    
    @GetMapping("/users")
    User getUsers(){

      User s=new User("dwdsdsd23","eusebian eduard  ","bmw_93@gmail.com");
        return  s;
    }


}
