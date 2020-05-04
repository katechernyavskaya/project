package com.gmail.ek.chernyavskaya.repository.impl;

import com.gmail.ek.chernyavskaya.repository.ArticleRepository;
import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericDaoImpl<Long, Article> implements ArticleRepository {

    @Override
    public List<Article> findAll() {
        String queryString = "FROM " + clazz.getSimpleName() + " a " +
                "ORDER BY date DESC";
        Query query = entityManager.createQuery(queryString);
        return (List<Article>) query.getResultList();
    }

    @Override
    public List<Article> findAllWithPagination(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
        Root<Article> root = criteriaQuery.from(Article.class);
        criteriaQuery.select(root);
        TypedQuery<Article> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pagination.getStartElement());
        typedQuery.setMaxResults(pagination.getElementsPerPage());
        return typedQuery.getResultList();
    }
}