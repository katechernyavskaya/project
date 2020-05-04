package com.gmail.ek.chernyavskaya.repository;

import java.util.List;

public interface GenericDao<I, T> {
    T add(T entity);

    T findById(I id);

    List<T> findAll();

    void delete(T entity);

    void merge(T entity);

}