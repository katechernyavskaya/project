package com.gmail.ek.chernyavskaya.repository.impl;

import com.gmail.ek.chernyavskaya.repository.GenericDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public abstract class GenericDaoImpl<I, T> implements GenericDao<I, T> {

    protected Class<T> clazz;
    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public T add(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T findById(I id) {
        return entityManager.find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        String query = "from " + clazz.getName() + " c";
        Query q = entityManager.createQuery(query);
        return q.getResultList();
    }

    @Transactional
    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void merge(T entity) {
        entityManager.merge(entity);
    }
}