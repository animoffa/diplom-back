package com.study.petproject.controller;

import com.study.petproject.config.CurrentUser;
import com.study.petproject.config.RuntimeUserInfo;
import com.study.petproject.model.Article;
import com.study.petproject.model.User;
import com.study.petproject.service.ArticleService;
import com.study.petproject.service.JwtService;
import com.study.petproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    private final ArticleService articleService;
    private final AuthenticationManager authManager;

    public UserController(JwtService jwtService, UserService userService, ArticleService articleService, AuthenticationManager authManager) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.articleService = articleService;
        this.authManager = authManager;
    }
    @GetMapping("/me")
        public Optional<User> getUserInfo(@CurrentUser RuntimeUserInfo userInfo) {
            return userService.findUserByEmail(userInfo.getUsername());
        }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAll();
    }

    @PutMapping("/me")
    public void editUserInfo(@RequestBody User user) {
        userService.edit(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User user) {
        if (!userService.login(user)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new JwtResponse(authAndReturnJWT(user)));
    }

    private String authAndReturnJWT(User user) {
        // с помощью authManager авторизуем пользователя
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.email,
                        user.password
                )
        );
        return authAndReturnJWT(auth, user.email);
    }

    private String authAndReturnJWT(Authentication auth, String email) {
        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtService.generateToken(email);
    }

    @PostMapping("/registration")
    public boolean registration(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping("/articles/{articleId}")
    public Article likeArticle(@CurrentUser RuntimeUserInfo userInfo, @PathVariable long articleId) {
        userService.likeArticle(articleId, userInfo.user.id);
        return articleService.getOne(articleId).orElse(null);
    }
    @PutMapping("/comment/{articleId}")
    public Article commentArticle(@CurrentUser RuntimeUserInfo userInfo, @PathVariable long articleId, @RequestBody String comment) {
        userService.commentArticle(articleId, userInfo.user.id, comment);
        return articleService.getOne(articleId).orElse(null);
    }

    @PostMapping("/scrapping")
    public String scrapping(@RequestBody String params){
            return userService.scrappingELibrary("https://www.elibrary.ru/authors.asp", params);

    };
}
