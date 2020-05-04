package com.gmail.ek.chernyavskaya.repository;

import com.gmail.ek.chernyavskaya.repository.model.Invoice;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.User;

import java.util.List;

public interface OrderRepository extends GenericDao<Long, Invoice> {

    List<Invoice> getUserOrders(User user);

    List<Invoice> findAllWithPagination(Pagination pagination);

    List<Invoice> findAllUserOrdersWithPagination(Pagination pagination, User user);

}