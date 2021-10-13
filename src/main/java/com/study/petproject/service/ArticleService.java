package com.study.petproject.service;

import com.study.petproject.model.Article;
import com.study.petproject.repo.ArticleRepo;
import org.springframework.stereotype.Component;

@Component
public class ArticleService extends ObjectService<Article> {

    private final ArticleRepo articleRepo;

    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    @Override
    public void add(Article obj) {
        System.out.println("add user");
        articleRepo.save(obj);
    }

    @Override
    public void edit(Article obj) {
        articleRepo.save(obj);
    }

    @Override
    public  void getAll(Article obj) {
        articleRepo.findAll();
    }
    @Override
    public  void getOne(Article obj) {
        articleRepo.findById(obj.id);
    }

    @Override
    public void delete(Article obj) {
        articleRepo.delete(obj);
    }
}
