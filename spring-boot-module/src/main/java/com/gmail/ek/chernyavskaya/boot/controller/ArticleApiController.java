package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.repository.ArticleRepository;
import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.service.ArticleService;
import com.gmail.ek.chernyavskaya.service.model.ArticleDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    public ArticleApiController(ArticleService articleService, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public List<ArticleDTO> getArticles() {
        return articleService.findAll();
    }

    @PostMapping
    public String addArticle(
            @Valid @RequestBody ArticleDTO article,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getValidationMessage(bindingResult);
        } else {
            articleService.add(article);
            return "Created Successfully";
        }
    }

    @GetMapping("/{id}")
    public ArticleDTO getArticleById(@PathVariable Long id) {
        return articleService.findById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteArticles(@PathVariable Long id) {
        Article article = articleRepository.findById(id);
        articleRepository.delete(article);
        return "Deleted Successfully";
    }

    private String getValidationMessage(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        List<String> message = new ArrayList<>();
        for (FieldError e : errors) {
            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
        }
        return message.toString();
    }

}
