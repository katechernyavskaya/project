package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.repository.CommentRepository;
import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.repository.model.Comment;
import com.gmail.ek.chernyavskaya.repository.model.User;
import com.gmail.ek.chernyavskaya.service.CommentService;
import com.gmail.ek.chernyavskaya.service.model.ArticleDTO;
import com.gmail.ek.chernyavskaya.service.model.CommentDTO;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private PasswordEncoder passwordEncoder;

    public CommentServiceImpl(CommentRepository commentRepository, PasswordEncoder passwordEncoder) {
        this.commentRepository = commentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<CommentDTO> findCommentsByArticleId(ArticleDTO articleDTO) {
        Article article = convertDTOToArticle(articleDTO);
        List<Comment> comments = commentRepository.findCommentsByArticle(article);
        List<CommentDTO> commentsDTO = comments.stream().map(this::convertCommentToDTO).collect(Collectors.toList());
        return commentsDTO;
    }

    @Override
    public CommentDTO findById(Long id) {
        Comment comment = commentRepository.findById(id);
        return convertCommentToDTO(comment);
    }

    private CommentDTO convertCommentToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setArticle(comment.getArticle());
        commentDTO.setCommentator(comment.getCommentator());
        commentDTO.setDate(comment.getDate());
        commentDTO.setText(comment.getText());
        return commentDTO;
    }

    private Article convertDTOToArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        if (articleDTO.getId() != null) {
            article.setId(articleDTO.getId());
        }
        User author = convertDTOToUser(articleDTO.getAuthor());
        article.setAuthor(author);
        article.setDate(articleDTO.getDate());
        article.setTitle(articleDTO.getTitle());
        article.setFullText(articleDTO.getFullText());
        return article;
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