package com.baumstaemme.backend.controller;

import com.baumstaemme.backend.entity.User;
import com.baumstaemme.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/addUser")
    public void  addUser(@RequestBody User user){
        userRepository.save(user);
    }
}
