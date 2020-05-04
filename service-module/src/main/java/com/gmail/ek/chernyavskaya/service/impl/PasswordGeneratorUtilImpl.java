package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.service.util.PasswordGeneratorUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.security.SecureRandom;

@Service
public class PasswordGeneratorUtilImpl implements PasswordGeneratorUtil {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Override
    public String generatePassword() {
        char[] possibleCharacters = ("\"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\\\|;:\\'\\\",<.>/?").toCharArray();
        int countOfCharacters = 8;
        int startPosition = 0;
        String password = RandomStringUtils.random(countOfCharacters, startPosition, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
        logger.info("ECH generatedPassword: " + password);
        return password;
    }
}