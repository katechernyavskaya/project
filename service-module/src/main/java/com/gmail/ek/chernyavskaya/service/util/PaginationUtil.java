package com.gmail.ek.chernyavskaya.service.util;

import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;

public interface PaginationUtil {

    PaginationDTO addPagination(PaginationDTO pagination, int countOfEntityInDatabase);
}
