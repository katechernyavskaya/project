package com.gmail.ek.chernyavskaya.service.util;

public interface PasswordSenderUtil {

    void sendEmailWithPasswordToUser(String userEmail, String password);
}
