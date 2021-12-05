package com.study.petproject.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.petproject.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RuntimeUserInfo implements UserDetails {
    @JsonIgnore
    public User user;

    public static RuntimeUserInfo create(User user) {
        RuntimeUserInfo newUser = new RuntimeUserInfo();
        newUser.user = user;
        return newUser;
    }

    @Override
    public String getUsername() {
        return user.email;
    }

    @Override
    public String getPassword() {
        return user.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuntimeUserInfo that = (RuntimeUserInfo) o;
        return Objects.equals(user.id, that.user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.id);
    }
}
