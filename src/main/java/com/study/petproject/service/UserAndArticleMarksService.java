package com.study.petproject.service;

import com.study.petproject.model.UserAndArticleMarks;
import com.study.petproject.repo.UserAndArticleMarksRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public List<UserAndArticleMarks> getAll() {
        return marksRepo.findAll();
    }
    @Override
    public Optional<UserAndArticleMarks> getOne(long id) {
        return marksRepo.findById(id);//TODO: здесь и в репо сделать нормальный тип
    }
    @Override
    public void edit(UserAndArticleMarks obj) {
        marksRepo.save(obj);
    }
    @Override
    public void delete(long id) {
        marksRepo.deleteById(id);
    }
}
