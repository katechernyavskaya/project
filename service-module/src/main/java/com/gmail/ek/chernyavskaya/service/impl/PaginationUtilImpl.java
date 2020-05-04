package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.util.PaginationUtil;
import org.springframework.stereotype.Service;

@Service
public class PaginationUtilImpl implements PaginationUtil {
    private static final Integer COUNT_PER_PAGE = 10;
    private static final Integer FIRST_PAGE = 1;

    @Override
    public PaginationDTO addPagination(PaginationDTO pagination, int countOfEntityInDatabase) {
        if (pagination == null) {
            pagination = new PaginationDTO();
        }
        if (pagination.getCurrentPage() == null) {
            pagination.setCurrentPage(FIRST_PAGE);
        }
        if (pagination.getElementsPerPage() == null) {
            pagination.setElementsPerPage(COUNT_PER_PAGE);
        }
        Integer amountPages = countOfEntityInDatabase / pagination.getElementsPerPage();
        if (countOfEntityInDatabase % pagination.getElementsPerPage() > 0) {
            amountPages++;
        }
        if (amountPages == 0) {
            amountPages++;
        }
        pagination.setAmountPages(amountPages);
        if (pagination.getAmountPages() < pagination.getCurrentPage()) {
            pagination.setCurrentPage(1);
        }
        return pagination;
    }


    public static Integer getStartElement(PaginationDTO paginationDTO) {
        return (paginationDTO.getCurrentPage() - 1) * paginationDTO.getElementsPerPage();
    }
}
