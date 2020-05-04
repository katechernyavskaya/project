package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.repository.ReviewRepository;
import com.gmail.ek.chernyavskaya.repository.model.Review;
import com.gmail.ek.chernyavskaya.service.ReviewService;
import com.gmail.ek.chernyavskaya.service.UserService;
import com.gmail.ek.chernyavskaya.service.model.*;
import com.gmail.ek.chernyavskaya.service.util.PaginationUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/reviews")
@SessionAttributes(types = ItemsWithPaginationDTO.class)
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final ReviewRepository reviewRepository;
    private final PaginationUtil paginationUtil;

    public ReviewController(ReviewService reviewService, UserService userService, ReviewRepository reviewRepository, PaginationUtil paginationUtil) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.reviewRepository = reviewRepository;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping
    public String showAllReviews(ReviewsWithPaginationDTO reviewsWithPagination, Model model) {
        List<ReviewDTO> allReviews = reviewService.findAll();
        int countOfReviews = allReviews.size();
        PaginationDTO pagination = paginationUtil.addPagination(reviewsWithPagination.getPagination(), countOfReviews);
        reviewsWithPagination.setPagination(pagination);
        List<ReviewDTO> reviews = reviewService.getAllReviewsWithPagination(pagination);
        reviewsWithPagination.setReviews(reviews);
        model.addAttribute("reviewsWithPagination", reviewsWithPagination);
        return "reviews";
    }

    @GetMapping("/add")
    public String addReview(Model model) {
        model.addAttribute("review", new ReviewDTO());
        return "add_review";
    }

    @PostMapping
    public String addReview(@Valid @ModelAttribute(name = "review") ReviewDTO review) throws SQLException {
        Date date = new java.sql.Date(System.currentTimeMillis());
        review.setCreationDate(date);
        UserDTO user = getCurrentUserDTO();
        review.setReviewer(user);
        review.setDisplayed(false);
        reviewService.add(review);
        return "redirect:/reviews";
    }

    @GetMapping("/{id}/delete")
    public String deleteReviewById(@PathVariable(name = "id") Long id) {
        Review review = reviewRepository.findById(id);
        reviewRepository.delete(review);
        return "redirect:/reviews";
    }

    @PostMapping("/changeReviewsDisplaying")
    public String changeReviewsDisplaying(
            @ModelAttribute(name = "changeReviewsDisplaying") ChangeReviewsDisplayingDTO changeReviewsDisplaying, Model model
    ) {
        reviewService.changeReviewsDisplaying(changeReviewsDisplaying.getReviewIds());
        return "redirect:/reviews";
    }

    private UserDTO getCurrentUserDTO() throws SQLException {
        UserDTO userDTO = userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return userDTO;
    }
}