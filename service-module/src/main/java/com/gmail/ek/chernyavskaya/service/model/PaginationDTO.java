package com.gmail.ek.chernyavskaya.service.model;

public class PaginationDTO {
    private Integer currentPage;
    private Integer elementsPerPage;
    private Integer amountPages;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getElementsPerPage() {
        return elementsPerPage;
    }

    public void setElementsPerPage(Integer elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
    }

    public Integer getAmountPages() {
        return amountPages;
    }

    public void setAmountPages(Integer amountPages) {
        this.amountPages = amountPages;
    }

    @Override
    public String toString() {
        return "PaginationDTO{" + "currentPage=" + currentPage + ", elementsPerPage=" + elementsPerPage + ", amountPages=" + amountPages + '}';
    }

}
