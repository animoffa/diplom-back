package com.study.petproject.service;

import com.study.petproject.model.User;
import com.study.petproject.repo.UserRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService extends ObjectService<User> {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void add(User obj) {
        System.out.println("add user");
        userRepo.save(obj);
    }

    @Override
    public void edit(User obj) {
        userRepo.save(obj);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }
    @Override
    public Optional<User> getOne(long id) {
        return userRepo.findById(id);
    }
    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
    }
}
