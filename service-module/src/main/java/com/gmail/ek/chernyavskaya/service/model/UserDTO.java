package com.gmail.ek.chernyavskaya.service.model;

import com.gmail.ek.chernyavskaya.repository.model.RoleEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDTO {

    private Long id;
    @NotNull
    @Email
    private String email;
    @Size(min = 1, max = 20, message = "Must be between 1 and 40 characters long")
    @NotNull
    private String name;
    @NotNull
    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters long")
    private String surname;
    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters long")
    private String patronymic;
    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters long")
    private String password;
    private Boolean isCouldNotBeDeleted;
    @NotNull
    private RoleEnum role;
    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters long")
    private String newPassword;
    private Boolean isDisabled;
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters long")
    private String address;
    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters long")
    private String phone;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Boolean getCouldNotBeDeleted() {
        return isCouldNotBeDeleted;
    }

    public void setCouldNotBeDeleted(Boolean couldNotBeDeleted) {
        isCouldNotBeDeleted = couldNotBeDeleted;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getDisabled() {
        return isDisabled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDisabled(Boolean disabled) {
        isDisabled = disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

}