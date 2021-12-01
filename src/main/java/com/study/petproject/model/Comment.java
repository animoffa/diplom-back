package com.study.petproject.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String text;
    public Instant date;

    @ManyToOne()
    public User author;

}
