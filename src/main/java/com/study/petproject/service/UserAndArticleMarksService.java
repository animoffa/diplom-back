package com.study.petproject.service;

import com.study.petproject.model.UserAndArticleMarks;
import com.study.petproject.repo.UserAndArticleMarksRepo;
import org.springframework.stereotype.Component;

@Component
public class UserAndArticleMarksService extends ObjectService<UserAndArticleMarks> {

    private final UserAndArticleMarksRepo marksRepo;

    public UserAndArticleMarksService(UserAndArticleMarksRepo marksRepo) {
        this.marksRepo = marksRepo;
    }

    @Override
    public void add(UserAndArticleMarks obj) {
        System.out.println("add user");
        marksRepo.save(obj);
    }

    @Override
    public  void getAll(UserAndArticleMarks obj) {
        marksRepo.findAll();
    }
    @Override
    public  void getOne(UserAndArticleMarks obj) {
        //marksRepo.findById(obj.id);//TODO: здесь и в репо сделать нормальный тип
    }
    @Override
    public void edit(UserAndArticleMarks obj) {
        marksRepo.save(obj);
    }
    @Override
    public void delete(UserAndArticleMarks obj) {
        marksRepo.delete(obj);
    }
}
