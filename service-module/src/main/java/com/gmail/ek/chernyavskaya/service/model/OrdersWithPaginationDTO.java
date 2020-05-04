package com.gmail.ek.chernyavskaya.service.model;

import java.util.List;

public class OrdersWithPaginationDTO {
    private PaginationDTO pagination;
    private List<InvoiceDTO> userOrders;

    public OrdersWithPaginationDTO() {
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    public List<InvoiceDTO> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(List<InvoiceDTO> userOrders) {
        this.userOrders = userOrders;
    }
}
