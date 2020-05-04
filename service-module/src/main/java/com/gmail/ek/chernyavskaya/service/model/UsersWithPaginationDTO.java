package com.gmail.ek.chernyavskaya.service.model;

import java.util.List;

public class UsersWithPaginationDTO {

    private PaginationDTO pagination;
    private List<UserDTO> users;
    private DeleteUsersDTO deleteUsers;

    public UsersWithPaginationDTO() {
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public DeleteUsersDTO getDeleteUsers() {
        return deleteUsers;
    }

    public void setDeleteUsers(DeleteUsersDTO deleteUsers) {
        this.deleteUsers = deleteUsers;
    }
}
