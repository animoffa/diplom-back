package com.study.petproject.service;

import com.study.petproject.model.Article;
import com.study.petproject.repo.ArticleRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ArticleService extends ObjectService<Article> {

    private final ArticleRepo articleRepo;

    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    @Override
    public void add(Article obj) {
        articleRepo.save(obj);
    }

    @Override
    public void edit(Article obj) {
        articleRepo.save(obj);
    }

    @Override
    public List<Article> getAll() {
        return articleRepo.findAll();
    }
    @Override
    public Optional<Article> getOne(long id) {
        return articleRepo.findById(id);
    }

    @Override
    public void delete(long id) {
        articleRepo.deleteById(id);
    }
}
