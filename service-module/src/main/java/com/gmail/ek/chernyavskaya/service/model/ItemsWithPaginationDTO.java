package com.gmail.ek.chernyavskaya.service.model;

import java.util.List;

public class ItemsWithPaginationDTO {
    private PaginationDTO pagination;
    private List<ItemDTO> items;

    public ItemsWithPaginationDTO() {
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
