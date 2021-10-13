package com.study.petproject.service;

import com.study.petproject.model.User;
import com.study.petproject.repo.UserRepo;
import org.springframework.stereotype.Component;

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
    public  void getAll(User obj) {
        userRepo.findAll();
    }
    @Override
    public  void getOne(User obj) {
        userRepo.findById(obj.id);
    }
    @Override
    public void delete(User obj) {
    }
}
