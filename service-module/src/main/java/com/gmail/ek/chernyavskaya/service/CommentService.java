package com.gmail.ek.chernyavskaya.service;

import com.gmail.ek.chernyavskaya.service.model.ArticleDTO;
import com.gmail.ek.chernyavskaya.service.model.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> findCommentsByArticleId(ArticleDTO articleDTO);

    CommentDTO findById(Long id);
}
