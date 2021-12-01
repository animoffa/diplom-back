package com.study.petproject.service;

import java.util.List;
import java.util.Optional;

public abstract class ObjectService<T> {
    public abstract List<T> getAll();
    public abstract Optional<T> getOne(long obj);
    public abstract void add(T obj);
    public abstract void edit(T obj);
    public abstract void delete(long obj);
}
