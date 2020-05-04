package com.gmail.ek.chernyavskaya.service.impl;

import com.gmail.ek.chernyavskaya.service.util.ProductCodeGeneratorUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ProductCodeGeneratorUtilImpl implements ProductCodeGeneratorUtil {

    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Override
    public String generateProductCode() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789").toCharArray();
        int countOfCharacters = 4;
        int startPosition = 0;
        String codeSuffix = "PROD_";
        String code = codeSuffix + RandomStringUtils.random(countOfCharacters, startPosition, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
        return code;
    }

}
