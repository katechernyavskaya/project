package com.gmail.ek.chernyavskaya.service;


import com.gmail.ek.chernyavskaya.service.model.ArticleDTO;
import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;

import java.util.List;

public interface ArticleService {

    List<ArticleDTO> findAll();

    ArticleDTO findById(Long id);

    ArticleDTO add(ArticleDTO articleDTO);

    void delete(ArticleDTO article);

    void merge(ArticleDTO articleDTO);

    List<ArticleDTO> getAllArticlesWithPagination(PaginationDTO paginationDTO);
}
