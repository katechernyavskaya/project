package com.gmail.ek.chernyavskaya;

import com.gmail.ek.chernyavskaya.repository.UserRepository;
import com.gmail.ek.chernyavskaya.repository.model.RoleEnum;
import com.gmail.ek.chernyavskaya.repository.model.User;
import com.gmail.ek.chernyavskaya.service.UserService;
import com.gmail.ek.chernyavskaya.service.impl.UserServiceImpl;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;
import com.gmail.ek.chernyavskaya.service.util.PaginationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaginationUtil paginationUtil;

    @BeforeEach
    public void setup() {
        this.userService = new UserServiceImpl(passwordEncoder, userRepository, paginationUtil);
    }

    @Test
    public void createValidUser_returnUser() {
        User user = new User();
        User returnedUser = new User();
        Long userId = 1L;
        returnedUser.setId(userId);
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = new UserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser).isNotNull();
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_Email() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getEmail()).isEqualTo(userDTO.getEmail());
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_Surname() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getSurname()).isEqualTo(userDTO.getSurname());
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_Name() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getName()).isEqualTo(userDTO.getName());
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_Patronymic() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getPatronymic()).isEqualTo(userDTO.getPatronymic());
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_Role() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getRole()).isEqualTo(userDTO.getRole());
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_Password() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getPassword()).isEqualTo(userDTO.getPassword());
    }


    @Test
    public void createValidUser_returnUserWithTheSameField_IsCouldNotBeDeleted() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getCouldNotBeDeleted()).isEqualTo(userDTO.getCouldNotBeDeleted());
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_IsDisabled() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getDisabled()).isEqualTo(userDTO.getDisabled());
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_Phone() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getPhone()).isEqualTo(userDTO.getPhone());
    }

    @Test
    public void createValidUser_returnUserWithTheSameField_Address() {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.add(user)).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO savedUser = userService.add(userDTO);
        assertThat(savedUser.getAddress()).isEqualTo(userDTO.getAddress());
    }

    @Test
    public void loadUserByEmail_returnUser() throws SQLException {
        User user = generateValidUser();
        User returnedUser = generateValidUser();
        when(userRepository.loadUserByEmail(user.getEmail())).thenReturn(returnedUser);
        UserDTO userDTO = generateValidUserDTO();
        UserDTO foundUser = userService.loadUserByEmail(userDTO.getEmail());
        assertThat(foundUser).isEqualTo(userDTO);
    }

    @Test
    public void findUserById_returnUser() {
        UserDTO user = generateValidUserDTO();
        user.setId(1L);
        User returnedUser = generateValidUser();
        Long userId = 1L;
        returnedUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(returnedUser);
        UserDTO returnedFromServiceUser = userService.findById(userId);
        assertThat(returnedFromServiceUser).isEqualTo(user);
    }

    private UserDTO generateValidUserDTO() {
        UserDTO user = new UserDTO();
        user.setName("Name");
        user.setSurname("Surname");
        user.setEmail("ech@test.com");
        user.setRole(RoleEnum.CUSTOMER_USER);
        user.setCouldNotBeDeleted(false);
        user.setPassword("Password");
        user.setPatronymic("Patronymic");
        user.setPhone("Phone");
        user.setAddress("Address");
        user.setDisabled(false);
        return user;
    }

    private User generateValidUser() {
        User user = new User();
        user.setName("Name");
        user.setSurname("Surname");
        user.setEmail("ech@test.com");
        user.setRole(RoleEnum.CUSTOMER_USER);
        user.setCouldNotBeDeleted(false);
        user.setPassword("Password");
        user.setPatronymic("Patronymic");
        user.setPhone("Phone");
        user.setAddress("Address");
        user.setDisabled(false);
        return user;
    }
}