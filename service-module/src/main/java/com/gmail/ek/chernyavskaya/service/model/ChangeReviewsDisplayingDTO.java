package com.gmail.ek.chernyavskaya.service.model;

import java.util.List;

public class ChangeReviewsDisplayingDTO {
    private List<Long> reviewIds;

    public ChangeReviewsDisplayingDTO() {
    }

    public List<Long> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Long> reviewIds) {
        this.reviewIds = reviewIds;
    }
}
