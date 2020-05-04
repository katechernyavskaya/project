package com.gmail.ek.chernyavskaya.service.model;

import java.util.List;

public class ReviewsWithPaginationDTO {
    private PaginationDTO pagination;
    private List<ReviewDTO> reviews;

    public ReviewsWithPaginationDTO() {
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

}
