package com.study.petproject.controller;

import com.study.petproject.model.User;
import com.study.petproject.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final JwtService jwtService;

    public UserController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return jwtService.generateToken(user.email);
    }
}
