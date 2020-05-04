package com.gmail.ek.chernyavskaya.service;

import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    UserDTO add(UserDTO userDTO);

    UserDTO loadUserByEmail(String email) throws SQLException;

    List<UserDTO> getAllUsersWithPagination(PaginationDTO paginationDTO);

    void deleteUsers(List<Long> userIdsToDelete);

    UserDTO findById(Long id);

    void merge(UserDTO userDTO);

    boolean checkIfValidOldPassword(UserDTO user, String oldPassword);

    List<UserDTO> findAll();
}