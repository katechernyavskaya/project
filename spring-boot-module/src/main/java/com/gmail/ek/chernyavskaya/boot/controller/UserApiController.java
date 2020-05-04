package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.service.UserService;
import com.gmail.ek.chernyavskaya.service.model.UserDTO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(
            @Valid @RequestBody UserDTO user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return getValidationMessage(bindingResult);
        } else {
            userService.add(user);
            return "Added Successfully";
        }
    }

    private String getValidationMessage(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        List<String> message = new ArrayList<>();
        for (FieldError e : errors) {
            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
        }
        return message.toString();
    }

}
