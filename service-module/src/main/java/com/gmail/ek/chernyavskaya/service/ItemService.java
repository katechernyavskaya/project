package com.gmail.ek.chernyavskaya.service;

import com.gmail.ek.chernyavskaya.service.model.ItemDTO;
import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;

import java.util.List;

public interface ItemService {
    List<ItemDTO> findAll();

    ItemDTO add(ItemDTO itemDTO);

    void delete(ItemDTO itemDTO);

    ItemDTO findById(Long id);

    ItemDTO copyItem(ItemDTO item);

    List<ItemDTO> getAllItemsWithPagination(PaginationDTO paginationDTO);
}
