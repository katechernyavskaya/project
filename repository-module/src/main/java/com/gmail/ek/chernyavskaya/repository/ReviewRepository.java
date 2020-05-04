package com.gmail.ek.chernyavskaya.repository;

import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.Review;

import java.util.List;

public interface ReviewRepository extends GenericDao<Long, Review> {

    List<Review> findAllWithPagination(Pagination pagination);

    void changeReviewsDisplaying(List<Long> reviewIds);
}