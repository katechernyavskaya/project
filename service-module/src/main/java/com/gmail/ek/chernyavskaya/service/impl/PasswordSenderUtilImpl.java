package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.service.util.PasswordSenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PasswordSenderUtilImpl implements PasswordSenderUtil {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendFromEmail;

    @Override
    public void sendEmailWithPasswordToUser(String userEmail, String password) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(userEmail);
        msg.setSubject("Your password for 'DevelopYourSkills'");
        msg.setText("Hello!\n \nPlease find your password for DevelopYourSkills service: \n" + password + "\n\nThanks!");
        msg.setFrom("developyourskillsproject@gmail.com");
        mailSender.send(msg);
    }

}
