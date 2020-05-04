package com.gmail.ek.chernyavskaya.repository.impl;

import com.gmail.ek.chernyavskaya.repository.CommentRepository;
import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.repository.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends GenericDaoImpl<Long, Comment> implements CommentRepository {

    @Override
    public List<Comment> findCommentsByArticle(Article article) {
        String queryString = "FROM " + clazz.getSimpleName() + " c " +
                "WHERE c.article=:article ORDER BY date DESC";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("article", article);
        return query.getResultList();
    }
}