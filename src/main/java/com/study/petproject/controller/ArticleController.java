package com.study.petproject.controller;

import com.study.petproject.model.Article;
import com.study.petproject.model.User;
import com.study.petproject.service.ArticleService;
import com.study.petproject.service.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<Article> getArticles() {
        return articleService.getAll();
    }

    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable long id) {
        return articleService.getOne(id).orElse(null);
    }

    @PostMapping ("/articles")
    public void postArticle(@RequestBody Article obj) {
        articleService.add(obj);
    }

    @PutMapping ("/articles")
    public void putArticle(@RequestBody Article obj) {
        articleService.edit(obj);
    }

    @DeleteMapping ("/articles/{id}")
    public void deleteArticle(@PathVariable long id) {
        articleService.delete(id);
    }
}
