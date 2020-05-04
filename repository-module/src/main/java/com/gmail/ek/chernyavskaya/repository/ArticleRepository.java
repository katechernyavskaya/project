package com.gmail.ek.chernyavskaya.repository;

import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;

import java.util.List;

public interface ArticleRepository extends GenericDao<Long, Article> {
    List<Article> findAllWithPagination(Pagination pagination);
}