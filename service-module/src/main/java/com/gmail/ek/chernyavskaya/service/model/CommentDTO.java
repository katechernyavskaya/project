package com.gmail.ek.chernyavskaya.service.model;

import com.gmail.ek.chernyavskaya.repository.model.Article;
import com.gmail.ek.chernyavskaya.repository.model.User;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

public class CommentDTO {
    private Long id;
    private Date date;
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters long")
    private String text;
    private Article article;
    private User commentator;

    public CommentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getCommentator() {
        return commentator;
    }

    public void setCommentator(User commentator) {
        this.commentator = commentator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDTO that = (CommentDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }
}
