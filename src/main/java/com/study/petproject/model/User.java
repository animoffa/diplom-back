package com.study.petproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String email;
    public String name;
    public String lastname;
    public String password;
    public Instant birthday;
    public String phone;
    public String status;
    public String address;
    public String about;
}
