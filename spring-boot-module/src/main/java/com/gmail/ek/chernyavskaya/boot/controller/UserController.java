package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.repository.model.RoleEnum;
import com.gmail.ek.chernyavskaya.service.UserService;
import com.gmail.ek.chernyavskaya.service.impl.PasswordGeneratorUtilImpl;
import com.gmail.ek.chernyavskaya.service.model.*;
import com.gmail.ek.chernyavskaya.service.util.PaginationUtil;
import com.gmail.ek.chernyavskaya.service.util.PasswordGeneratorUtil;
import com.gmail.ek.chernyavskaya.service.util.PasswordSenderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@SessionAttributes(types = UsersWithPaginationDTO.class)
public class UserController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final PasswordSenderUtil passwordSenderUtil;
    private final PaginationUtil paginationUtil;

    public UserController(UserService userService, PasswordSenderUtil passwordSenderUtil, PaginationUtil paginationUtil) {
        this.userService = userService;
        this.passwordSenderUtil = passwordSenderUtil;
        this.paginationUtil = paginationUtil;
    }

    @GetMapping
    public String showAllUsers(UsersWithPaginationDTO usersWithPagination, Model model) {
        List<UserDTO> allUsers = userService.findAll();
        int countOfUsers = allUsers.size();
        PaginationDTO pagination = paginationUtil.addPagination(usersWithPagination.getPagination(), countOfUsers);
        usersWithPagination.setPagination(pagination);
        List<UserDTO> users = userService.getAllUsersWithPagination(pagination);
        usersWithPagination.setUsers(users);
        DeleteUsersDTO deleteUsers = new DeleteUsersDTO();
        usersWithPagination.setDeleteUsers(deleteUsers);
        model.addAttribute("usersWithPagination", usersWithPagination);
        return "users";
    }

    @GetMapping("/add")
    public String registerUser(Model model) {
        model.addAttribute("user", new UserDTO());
        List<RoleEnum> roles = getAllRoles();
        model.addAttribute("roles", roles);
        return "add_user";
    }

    @PostMapping
    public String addUser(@Valid @ModelAttribute(name = "user") UserDTO user, RoleEnum role, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("role", role);
        PasswordGeneratorUtil passwordGeneratorUtil = new PasswordGeneratorUtilImpl();
        String newPassword = passwordGeneratorUtil.generatePassword();
        user.setPassword(newPassword);
        user.setRole(role);
        user.setCouldNotBeDeleted(false);
        user.setDisabled(false);
        userService.add(user);
        passwordSenderUtil.sendEmailWithPasswordToUser(user.getEmail(), newPassword);
        return "redirect:/users";
    }

    @GetMapping("/{id}/password")
    public String changeUserPassword(Model model, @PathVariable(name = "id") Long id) {
        PasswordGeneratorUtil passwordGeneratorUtil = new PasswordGeneratorUtilImpl();
        String newPassword = passwordGeneratorUtil.generatePassword();
        UserDTO user = userService.findById(id);
        user.setPassword(newPassword);
        userService.merge(user);
        logger.info("New password: " + newPassword);
        passwordSenderUtil.sendEmailWithPasswordToUser(user.getEmail(), newPassword);
        return "success_password_update_by_admin";
    }

    @GetMapping("/{id}/role")
    public String findRoleById(Model model, @PathVariable(name = "id") Long id) {
        UserDTO user = userService.findById(id);
        model.addAttribute("user", user);
        List<RoleEnum> roles = getAllRoles();
        model.addAttribute("roles", roles);
        return "change_role_user";
    }

    @PostMapping("/{id}/role")
    public String changeUserRole(@PathVariable(name = "id") Long id,
                                 @ModelAttribute(name = "user") UserDTO user,
                                 @ModelAttribute(name = "role") RoleEnum role,
                                 Model model, BindingResult errors) {
        if (errors.hasErrors()) {
            return "change_role_user";
        }
        user = userService.findById(id);
        List<RoleEnum> roles = getAllRoles();
        model.addAttribute("roles", roles);
        user.setRole(role);
        userService.merge(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUsers(
            @ModelAttribute(name = "deleteUsersDTO") DeleteUsersDTO deleteUsersDTO, Model model
    ) {
        userService.deleteUsers(deleteUsersDTO.getUserIds());
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model) throws SQLException {
        UserProfileDTO user = convertCurrentUserDTOToProfileDTO();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute(name = "user") UserProfileDTO user) throws SQLException {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setEmail(currentUserEmail);
        UserDTO userDTO = convertCurrentProfileDTOToUserDTO(user);
        userService.merge(userDTO);
        return "redirect:/users/successProfileUpdate";
    }

    @GetMapping("/updatePassword")
    public String updateUserPassword(Model model) {
        UserPasswordChangeDTO user = new UserPasswordChangeDTO();
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setEmail(currentUserEmail);
        model.addAttribute("user", user);
        return "password_update";
    }

    @PostMapping("/updatePassword")
    public String updateUserPassword(@ModelAttribute(name = "user") UserPasswordChangeDTO user,
                                     BindingResult errors) throws SQLException {
        if (errors.hasErrors()) {
            return "password_update";
        }
        UserDTO userDTO = getCurrentUserDTO();
        String oldPasswordProvidedByUser = user.getOldPassword();
        if (!userService.checkIfValidOldPassword(userDTO, oldPasswordProvidedByUser)) {
            return "invalid_old_password";
        }
        String newPasswordProvidedByUser = user.getNewPassword();
        userDTO.setPassword(newPasswordProvidedByUser);
        userService.merge(userDTO);
        passwordSenderUtil.sendEmailWithPasswordToUser(userDTO.getEmail(), newPasswordProvidedByUser);
        return "redirect:/users/successPasswordUpdate";
    }

    @GetMapping("/successPasswordUpdate")
    public String showSuccessPasswordUpdate() {
        return "success_password_update_by_user";
    }

    @GetMapping("/successProfileUpdate")
    public String showSuccessProfileUpdate() {
        return "success_profile_update";
    }

    @GetMapping("/invalidOldPassword")
    public String showInvalidOldPassword() {
        return "invalid_old_password";
    }

    private UserDTO getCurrentUserDTO() throws SQLException {
        return userService.loadUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private List<RoleEnum> getAllRoles() {
        return Arrays.stream(RoleEnum.values()).collect(Collectors.toList());
    }

    private UserDTO convertCurrentProfileDTOToUserDTO(UserProfileDTO user) throws SQLException {
        UserDTO userDTO = getCurrentUserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }

    private UserProfileDTO convertCurrentUserDTOToProfileDTO() throws SQLException {
        UserProfileDTO user = new UserProfileDTO();
        UserDTO userDTO = getCurrentUserDTO();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        return user;
    }
}