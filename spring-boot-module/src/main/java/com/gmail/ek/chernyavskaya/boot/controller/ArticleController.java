package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.repository.ArticleRepository;
import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.service.ArticleService;
import com.gmail.ek.chernyavskaya.service.CommentService;
import com.gmail.ek.chernyavskaya.service.UserService;
import com.gmail.ek.chernyavskaya.service.model.ArticlesWithPaginationDTO;
import com.gmail.ek.chernyavskaya.service.model.*;
import com.gmail.ek.chernyavskaya.service.util.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/articles")
@SessionAttributes(types = ArticlesWithPaginationDTO.class)
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final PaginationUtil paginationUtil;
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public ArticleController(ArticleService articleService, CommentService commentService, UserService userService, ArticleRepository articleRepository, PaginationUtil paginationUtil) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
        this.articleRepository = articleRepository;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping
    public String showAllNews(ArticlesWithPaginationDTO articlesWithPagination, Model model) {
        List<ArticleDTO> allArticles = articleService.findAll();
        int countOfArticles = allArticles.size();
        PaginationDTO pagination = paginationUtil.addPagination(articlesWithPagination.getPagination(), countOfArticles);
        articlesWithPagination.setPagination(pagination);
        List<ArticleDTO> articles = articleService.getAllArticlesWithPagination(pagination);
        articlesWithPagination.setArticles(articles);
        model.addAttribute("articlesWithPagination", articlesWithPagination);
        return "articles";
    }

    @GetMapping("/add")
    public String addArticle(Model model) {
        model.addAttribute("article", new ArticleDTO());
        return "add_article";
    }

    @PostMapping
    public String postArticle(@Valid
                              @ModelAttribute(name = "article") ArticleDTO article,
                              Model model,
                              BindingResult errors) {
        if (errors.hasErrors()) {
            return "articles";
        }
        model.addAttribute("article", article);
        UserDTO user = null;
        try {
            user = getCurrentUserDTO();
        } catch (SQLException e) {
            logger.error("Post article failed: no current user found in db");
        }
        article.setAuthor(user);
        articleService.add(article);
        return "redirect:/articles";
    }

    @GetMapping("/{id}")
    public String findArticleById(Model model, @PathVariable(name = "id") Long id) {
        ArticleDTO article = articleService.findById(id);
        model.addAttribute("article", article);
        List<CommentDTO> comments = commentService.findCommentsByArticleId(article);
        model.addAttribute("comments", comments);
        return "article";
    }

    @GetMapping("/{id}/edit")
    public String editArticle(Model model, @PathVariable(name = "id") Long id) {
        ArticleDTO article = articleService.findById(id);
        model.addAttribute("article", article);
        return "edit_article";
    }

    @PostMapping("/{id}")
    public String updateArticle(@PathVariable(name = "id") Long id,
                                @ModelAttribute(name = "article") ArticleDTO article,
                                Model model, BindingResult errors) {
        if (errors.hasErrors()) {
            return "edit_article";
        }
        article = articleService.findById(id);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        article.setDate(date);
        articleService.merge(article);
        return "redirect:/articles";
    }

    @GetMapping("/{id}/delete")
    public String deleteArticleById(@PathVariable(name = "id") Long id) {
        Article article = articleRepository.findById(id);
        articleRepository.delete(article);
        return "redirect:/articles";
    }

    private UserDTO getCurrentUserDTO() throws SQLException {
        UserDTO userDTO = userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return userDTO;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        int dateLength = 10;
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, dateLength));
    }

}