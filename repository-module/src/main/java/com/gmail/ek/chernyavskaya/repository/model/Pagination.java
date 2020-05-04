package com.gmail.ek.chernyavskaya.repository.model;

import java.util.Objects;

public class Pagination {
    private Integer startElement;
    private Integer elementsPerPage;

    public Integer getStartElement() {
        return startElement;
    }

    public void setStartElement(Integer startElement) {
        this.startElement = startElement;
    }

    public Integer getElementsPerPage() {
        return elementsPerPage;
    }

    public void setElementsPerPage(Integer elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagination that = (Pagination) o;
        return Objects.equals(startElement, that.startElement) &&
                Objects.equals(elementsPerPage, that.elementsPerPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startElement, elementsPerPage);
    }
}
