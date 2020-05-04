package com.gmail.ek.chernyavskaya.repository.impl;

import com.gmail.ek.chernyavskaya.repository.OrderRepository;
import com.gmail.ek.chernyavskaya.repository.model.Invoice;
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
public class OrderRepositoryImpl extends GenericDaoImpl<Long, Invoice> implements OrderRepository {

    @Override
    public List<Invoice> getUserOrders(User user) {
        String queryString = "FROM " + clazz.getSimpleName() + " i " +
                "WHERE i.user=:user";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("user", user);
        return (List<Invoice>) query.getResultList();
    }

    @Override
    public List<Invoice> findAllWithPagination(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        Root<Invoice> root = criteriaQuery.from(Invoice.class);
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get("date")));
        TypedQuery<Invoice> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pagination.getStartElement());
        typedQuery.setMaxResults(pagination.getElementsPerPage());
        return typedQuery.getResultList();
    }

    @Override
    public List<Invoice> findAllUserOrdersWithPagination(Pagination pagination, User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteriaQuery = criteriaBuilder.createQuery(Invoice.class);
        Root<Invoice> root = criteriaQuery.from(Invoice.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("user"), user)); ///
        criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get("date")));
        TypedQuery<Invoice> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pagination.getStartElement());
        typedQuery.setMaxResults(pagination.getElementsPerPage());
        return typedQuery.getResultList();
    }
}
