package com.study.petproject.controller;

import com.study.petproject.config.CurrentUser;
import com.study.petproject.config.RuntimeUserInfo;
import com.study.petproject.model.Article;
import com.study.petproject.model.User;
import com.study.petproject.service.JwtService;
import com.study.petproject.service.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authManager;

    public UserController(JwtService jwtService, UserService userService, AuthenticationManager authManager) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User user) {
        System.out.println("USER PASS = " + user.password);
        if (!userService.login(user)) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("USER PASS 2 = " + user.password);
        return ResponseEntity.ok(new JwtResponse(authAndReturnJWT(user)));
    }

    private String authAndReturnJWT(User user) {
        System.out.println("USER PASS 3 =" + user.password);
        // с помощью authManager авторизуем пользователя
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.email,
                        user.password
                )
        );
        System.out.println("GENERATD ========= " + authAndReturnJWT(auth, user.email));
        return authAndReturnJWT(auth, user.email);
    }

    private String authAndReturnJWT(Authentication auth, String email) {
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println("KEKEKE");
        return jwtService.generateToken(email);
    }

    @PostMapping("/registration")
    public boolean registration(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/like/{articleId}}")
    public void postArticle(@CurrentUser RuntimeUserInfo userInfo, @PathVariable long articleId) {
        userService.likeArticle(articleId, userInfo.user.id);
    }
}
