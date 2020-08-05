package com.example.stocks.controller;

import com.example.stocks.domain.User;
import com.example.stocks.repository.UserRepository;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

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
