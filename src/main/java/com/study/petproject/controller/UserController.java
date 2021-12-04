package com.study.petproject.controller;

import com.study.petproject.model.Article;
import com.study.petproject.model.User;
import com.study.petproject.service.JwtService;
import com.study.petproject.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return jwtService.generateToken(user.email);
    }

    @PostMapping ("/like/{articleId}/{userId}")
    public void postArticle(@PathVariable long articleId, @PathVariable long userId ) {
        userService.likeArticle(articleId, userId);
    }
}
