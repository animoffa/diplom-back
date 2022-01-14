package com.study.petproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    public String img;
    public String lastname;
    public String password;
    public String spin;
    public String company;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "UTC")
    public Instant birthday;
    public String phone;
    public String status;
    public String address;
    public String about;

    @ManyToMany(mappedBy = "likeList")
    private Set<Article> likes = new HashSet<>();
}
