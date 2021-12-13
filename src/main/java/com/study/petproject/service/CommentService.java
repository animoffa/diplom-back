package com.study.petproject.service;

import com.study.petproject.model.Comment;
import com.study.petproject.model.User;
import com.study.petproject.repo.CommentRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommentService extends ObjectService<Comment> {

    private final CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public void add(Comment obj) {
        commentRepo.save(obj);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepo.findAll();
    }
    @Override
    public Optional<Comment> getOne(long id) {
        return commentRepo.findById(id);
    }
    @Override
    public void edit(Comment obj) {
        commentRepo.save(obj);
    }
    @Override
    public void delete(long id) {
        commentRepo.deleteById(id);
    }
}
