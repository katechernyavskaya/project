package com.gmail.ek.chernyavskaya.service;

import com.gmail.ek.chernyavskaya.service.model.InvoiceDTO;
import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;

import java.util.List;

public interface OrderService {

    InvoiceDTO add(InvoiceDTO order);

    List<InvoiceDTO> findAll();

    InvoiceDTO findById(Long id);

    List<InvoiceDTO> getUserOrders(UserDTO userDTO);

    List<InvoiceDTO> getAllOrdersWithPagination(PaginationDTO paginationDTO);

    List<InvoiceDTO> getAllUserOrdersWithPagination(PaginationDTO paginationDTO, UserDTO userDTO);

    void merge(InvoiceDTO orderDTO);
}