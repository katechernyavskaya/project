package com.gmail.ek.chernyavskaya.repository.impl;

import com.gmail.ek.chernyavskaya.repository.UserRepository;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericDaoImpl<Long, User> implements UserRepository {
    @Override
    public User loadUserByEmail(String email) {
        String queryString = "FROM " + clazz.getSimpleName() + " u " +
                "WHERE u.email=:email";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public void deleteUsers(List<Long> userIdsToDelete) {
        String queryString = "DELETE FROM " + clazz.getSimpleName() + " u " +
                "WHERE u.id IN (:userIds) AND u.isCouldNotBeDeleted=false";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("userIds", userIdsToDelete);
        query.executeUpdate();
    }

    @Override
    public List findAllWithPagination(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pagination.getStartElement());
        typedQuery.setMaxResults(pagination.getElementsPerPage());
        return typedQuery.getResultList();
    }

}
