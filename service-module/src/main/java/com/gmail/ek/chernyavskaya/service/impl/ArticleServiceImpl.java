package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.repository.ArticleRepository;
import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.User;
import com.gmail.ek.chernyavskaya.service.ArticleService;
import com.gmail.ek.chernyavskaya.service.model.ArticleDTO;
import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.ek.chernyavskaya.service.impl.PaginationUtilImpl.getStartElement;

@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {
    private PasswordEncoder passwordEncoder;
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(PasswordEncoder passwordEncoder, ArticleRepository articleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleDTO> findAll() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::convertArticleToDTO).collect(Collectors.toList());
    }

    @Override
    public ArticleDTO findById(Long id) {
        Article article = articleRepository.findById(id);
        return convertArticleToDTO(article);
    }

    @Override
    public ArticleDTO add(ArticleDTO articleDTO) {
        Article article = convertDTOToArticle(articleDTO);
        Article returnedArticle = articleRepository.add(article);
        return convertArticleToDTO(returnedArticle);
    }

    @Override
    public void delete(ArticleDTO articleDTO) {
        Article article = convertDTOToArticle(articleDTO);
        articleRepository.delete(article);
    }

    @Override
    public void merge(ArticleDTO articleDTO) {
        Article article = convertDTOToArticle(articleDTO);
        articleRepository.merge(article);
    }

    @Override
    public List<ArticleDTO> getAllArticlesWithPagination(PaginationDTO paginationDTO) {
        Pagination pagination = convertDTOToPagination(paginationDTO);
        List<Article> articles = articleRepository.findAllWithPagination(pagination);
        return articles.stream().map(this::convertArticleToDTO).collect(Collectors.toList());

    }

    private Pagination convertDTOToPagination(PaginationDTO paginationDTO) {
        Pagination pagination = new Pagination();
        pagination.setElementsPerPage(paginationDTO.getElementsPerPage());
        pagination.setStartElement(getStartElement(paginationDTO));
        return pagination;
    }

    private ArticleDTO convertArticleToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        UserDTO userDTO = convertUserToDTO(article.getAuthor());
        articleDTO.setAuthor(userDTO);
        articleDTO.setDate(article.getDate());
        articleDTO.setTitle(article.getTitle());
        String fullText = article.getFullText();
        articleDTO.setFullText(fullText);
        String shortText = convertFullTextToShortText(fullText);
        articleDTO.setShortText(shortText);
        return articleDTO;
    }

    private String convertFullTextToShortText(String fullText) {
        String shortText = "";
        int startIndex = 0;
        int endIndex = 195;
        int shortTextLength = 200;
        if (fullText.length() < shortTextLength)
            shortText = fullText;
        else {
            shortText = fullText.substring(startIndex, endIndex).concat(" ...");
        }
        return shortText;
    }

    private Article convertDTOToArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        User author = convertDTOToUser(articleDTO.getAuthor());
        article.setAuthor(author);
        article.setDate(articleDTO.getDate());
        article.setTitle(articleDTO.getTitle());
        article.setFullText(articleDTO.getFullText());
        return article;
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setCouldNotBeDeleted(user.getCouldNotBeDeleted());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    private User convertDTOToUser(UserDTO userDTO) {
        User user = new User();
        if (userDTO.getId() != null) {
            user.setId(userDTO.getId());
        }
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setCouldNotBeDeleted(userDTO.getCouldNotBeDeleted());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(userDTO.getRole());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        return user;
    }
}