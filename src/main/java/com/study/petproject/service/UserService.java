package com.study.petproject.service;

import com.study.petproject.model.User;
import com.study.petproject.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class UserService extends ObjectService<User> {

    private final UserRepo userRepo;
    private final ArticleService articleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, ArticleService articleService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.articleService = articleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty()) {
            User user = new User();
            user.name = "AAA";
            user.lastname = "BBB";
            user.about = "ABOUT";
            user.address = "ADDRESS";
            user.birthday = Instant.now();
            user.company = "COMPANY";
            user.email = "TEST@EMAIL.RU";
            user.password = "AAAA";
            user.phone = "+7123123123";
            user.status = "OKOKOKOKO";
            userRepo.save(user);
        }
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

    public void likeArticle(long articleId, long userId) {
        articleService.getOne(articleId)
                .ifPresent(existingArticle ->
                        getOne(userId).ifPresent(existingUser -> {
                            existingArticle.likeList.add(existingUser);
                            articleService.edit(existingArticle);
                        }));
    }

    public boolean register(User user) {
        Optional<User> existsUser = findUserByEmail(user.email);
        if (existsUser.isPresent()) {
            return false;
        }
        user.password = passwordEncoder.encode(user.password);
        add(user);
        return true;
    }

    private Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public boolean login(User user) {
        Optional<User> existsUser = findUserByEmail(user.email);
        if (existsUser.isEmpty()) {
            return false;
        }
        User passUser = existsUser.get();
        return passwordEncoder.matches(user.password, passUser.password);
    }
}
