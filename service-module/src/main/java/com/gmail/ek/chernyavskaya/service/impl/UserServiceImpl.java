package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.repository.UserRepository;
import com.gmail.ek.chernyavskaya.repository.model.Pagination;
import com.gmail.ek.chernyavskaya.repository.model.User;
import com.gmail.ek.chernyavskaya.service.UserService;
import com.gmail.ek.chernyavskaya.service.model.PaginationDTO;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;
import com.gmail.ek.chernyavskaya.service.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.ek.chernyavskaya.service.impl.PaginationUtilImpl.getStartElement;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, PaginationUtil paginationUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        User user = convertDTOToUser(userDTO);
        User userSaved = userRepository.add(user);
        return convertUserToDTO(userSaved);
    }

    @Override
    public UserDTO loadUserByEmail(String email) throws SQLException {
        User user = userRepository.loadUserByEmail(email);
        return convertUserToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsersWithPagination(PaginationDTO paginationDTO) {
        Pagination pagination = convertDTOToPagination(paginationDTO);
        List<User> users = userRepository.findAllWithPagination(pagination);
        return users.stream().map(this::convertUserToDTO).collect(Collectors.toList());
    }

    private Pagination convertDTOToPagination(PaginationDTO paginationDTO) {
        Pagination pagination = new Pagination();
        pagination.setElementsPerPage(paginationDTO.getElementsPerPage());
        pagination.setStartElement(getStartElement(paginationDTO));
        return pagination;
    }

    @Override
    public void deleteUsers(List<Long> userIdsToDelete) {
        userRepository.deleteUsers(userIdsToDelete);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id);
        return convertUserToDTO(user);
    }

    @Override
    public boolean checkIfValidOldPassword(UserDTO user, String oldPassword) {
        String currentUserPassword = user.getPassword();
        return passwordEncoder.matches(oldPassword, currentUserPassword);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertUserToDTO).collect(Collectors.toList());
    }

    @Override
    public void merge(UserDTO userDTO) {
        User user = convertDTOToUser(userDTO);
        userRepository.merge(user);
    }

    private UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setCouldNotBeDeleted(user.getCouldNotBeDeleted());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        userDTO.setDisabled(user.getDisabled());
        return userDTO;
    }

    private User convertDTOToUser(UserDTO userDTO) {
        User user = new User();
        if (userDTO.getId() != null) {
            user.setId(userDTO.getId());
        }
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setCouldNotBeDeleted(userDTO.getCouldNotBeDeleted());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(userDTO.getRole());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setDisabled(userDTO.getDisabled());
        return user;
    }

}