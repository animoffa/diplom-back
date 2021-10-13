package com.study.petproject.service;

import com.study.petproject.model.Comment;
import com.study.petproject.model.User;
import com.study.petproject.repo.CommentRepo;
import org.springframework.stereotype.Component;

@Component
public class CommentService extends ObjectService<Comment> {

    private final CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public void add(Comment obj) {
        System.out.println("add user");
        commentRepo.save(obj);
    }

    @Override
    public  void getAll(Comment obj) {
        commentRepo.findAll();
    }
    @Override
    public  void getOne(Comment obj) {
        commentRepo.findById(obj.id);
    }
    @Override
    public void edit(Comment obj) {
        commentRepo.save(obj);
    }
    @Override
    public void delete(Comment obj) {
        commentRepo.delete(obj);
    }
}
