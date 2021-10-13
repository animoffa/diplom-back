package com.study.petproject.service;

public abstract class ObjectService<T> {
    public abstract void getAll(T obj);
    public abstract void getOne(T obj);
    public abstract void add(T obj);
    public abstract void edit(T obj);
    public abstract void delete(T obj);
}
