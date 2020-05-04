package com.gmail.ek.chernyavskaya.repository.impl;

import com.gmail.ek.chernyavskaya.repository.ReviewRepository;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericDaoImpl<Long, Review> implements ReviewRepository {
    @Override
    public List<Review> findAllWithPagination(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> root = criteriaQuery.from(Review.class);
        criteriaQuery.select(root);
        TypedQuery<Review> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pagination.getStartElement());
        typedQuery.setMaxResults(pagination.getElementsPerPage());
        return typedQuery.getResultList();
    }

    @Override
    public void changeReviewsDisplaying(List<Long> reviewIds) {
        String queryString = "UPDATE " + clazz.getSimpleName() + " r " +
                "SET r.isDisplayed =: isDisplayed WHERE r.id IN (:reviewIds)";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("isDisplayed", true);
        query.setParameter("reviewIds", reviewIds);
        query.executeUpdate();
    }
}
