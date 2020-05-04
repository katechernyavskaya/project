package com.gmail.ek.chernyavskaya.service.model;

import java.util.List;

public class ArticlesWithPaginationDTO {

    private PaginationDTO pagination;
    private List<ArticleDTO> articles;

    public ArticlesWithPaginationDTO() {
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }
}
