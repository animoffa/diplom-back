package com.study.petproject.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;

    @ManyToOne()
    public User author;

    @ManyToMany()
    @JoinTable(
            name = "user_and_article_likes",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<User> likeList = new HashSet<>();

    public String text;
    public Instant date;

}
