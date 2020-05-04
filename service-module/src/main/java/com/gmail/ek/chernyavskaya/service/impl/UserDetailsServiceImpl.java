package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.service.UserService;
import com.gmail.ek.chernyavskaya.service.model.AppUser;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO userDTO = null;
        try {
            userDTO = userService.loadUserByEmail(username);
        } catch (SQLException e) {
            logger.error("SQL exception during load user by user name in Repository");
        }
        return new AppUser(userDTO);
    }
}