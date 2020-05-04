package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.repository.CommentRepository;
import com.gmail.ek.chernyavskaya.repository.model.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/{id}/delete")
    public String deleteCommentById(@PathVariable(name = "id") Long id) {
        Comment comment = commentRepository.findById(id);
        commentRepository.delete(comment);
        return "redirect:/articles";
    }
}