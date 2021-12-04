package com.study.petproject.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String email;
    public String name;
    public String lastname;
    public String password;
    public String company;
    public Instant birthday;
    public String phone;
    public String status;
    public String address;
    public String about;

    @ManyToMany(mappedBy = "likeList")
    private Set<Article> likes = new HashSet<>();
}
