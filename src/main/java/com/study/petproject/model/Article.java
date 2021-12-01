package com.study.petproject.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;

    @ManyToOne()
    public User author;

    public String text;
    public Instant date;

}
