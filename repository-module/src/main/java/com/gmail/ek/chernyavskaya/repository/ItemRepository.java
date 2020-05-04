package com.gmail.ek.chernyavskaya.repository;

import com.gmail.ek.chernyavskaya.repository.model.Item;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;

import java.util.List;

public interface ItemRepository extends GenericDao<Long, Item> {
    List<Item> findAllWithPagination(Pagination pagination);
}