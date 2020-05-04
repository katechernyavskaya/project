package com.gmail.ek.chernyavskaya.repository;

import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.repository.model.Comment;

import java.util.List;

public interface CommentRepository extends GenericDao<Long, Comment> {
    List<Comment> findCommentsByArticle(Article article);
}