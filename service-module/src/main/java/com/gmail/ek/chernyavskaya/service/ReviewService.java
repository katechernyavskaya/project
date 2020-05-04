package com.gmail.ek.chernyavskaya.service;

import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.model.ReviewDTO;

import java.util.List;

public interface ReviewService {

    ReviewDTO add(ReviewDTO reviewDTO);

    List<ReviewDTO> findAll();

    List<ReviewDTO> getAllReviewsWithPagination(PaginationDTO paginationDTO);

    void changeReviewsDisplaying(List<Long> reviewIds);
}