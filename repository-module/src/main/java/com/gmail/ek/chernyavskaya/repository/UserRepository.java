package com.gmail.ek.chernyavskaya.repository;

import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends GenericDao<Long, User> {
    User loadUserByEmail(String email) throws SQLException;

    void deleteUsers(List<Long> userIdsToDelete);

    List<User> findAllWithPagination(Pagination pagination);
}