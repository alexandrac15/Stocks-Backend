package com.example.stocks.controller;

import com.example.stocks.domain.Company;
import com.example.stocks.domain.User;
import com.example.stocks.repositories.CompanyRepository;
import com.example.stocks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private static final String CLIENT_ID = "1016502741069-k249od2so3r40pn14815cjfb3c4nrdde.apps.googleusercontent.com";
    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @PostMapping("/adduser")
    User addUser(){
        User user =new User("sdgwd","wuegduwy","jhasjgahg@gmail.com");

        return  userRepository.save(user);
    }

    @PostMapping("/trackCompany/{userId}/{companyId}")
    int trackCompany(@PathVariable String userId, @PathVariable int companyId){
        User user = userRepository.findByGoogleUserId(userId);
        Company company = companyRepository.findById(companyId).get();
        if(user == null)
        {
            return 1;
        }
        user.addTrackedCompany(company);
        userRepository.save(user);
        return 0;
    }

    @GetMapping("/users")
    User getUsers(){
        User user = new User("dwdsdsd23","eusebian eduard  ","bmw_93@gmail.com");
        return  user;
    }


}
