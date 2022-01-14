package com.study.petproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    public String other_authors;
    public String link;

    @ManyToOne()
    public User author;

    @ManyToMany()
    @JoinTable(
            name = "user_and_article_likes",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<User> likeList = new HashSet<>();

    @ManyToMany()
    @JoinTable(
            name = "article_and_comment",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    public Set<Comment> commentList = new HashSet<>();

    public String text;

    public Instant date;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
//    public Instant getDate() {
//        return this.date;
//    }

}
