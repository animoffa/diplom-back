package com.study.petproject.controller;

import com.study.petproject.model.Article;
import com.study.petproject.model.User;
import com.study.petproject.service.JwtService;
import com.study.petproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
         if (!userService.login(user)) {
             return ResponseEntity.badRequest().build();
         }
        return ResponseEntity.ok(jwtService.generateToken(user.email));
    }

    @PostMapping("/registration")
    public boolean registration(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping ("/like/{articleId}/{userId}")
    public void postArticle(@PathVariable long articleId, @PathVariable long userId ) {
        userService.likeArticle(articleId, userId);
    }
}
