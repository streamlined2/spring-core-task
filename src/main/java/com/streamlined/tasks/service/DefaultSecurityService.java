package com.streamlined.tasks.service;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultSecurityService implements SecurityService {

    private static final int PASSWORD_LENGTH = 10;
    private static final char START_PASSWORD_CHAR = '\u0021';
    private static final char END_PASSWORD_CHAR = '\u007F';
    private static final int SALT_OFFSET = 10;

    private final PasswordEncoder passwordEncoder;
    private final Random random;

    public DefaultSecurityService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        random = new SecureRandom();
    }

    @Override
    public char[] getNewPassword() {
        char[] password = new char[PASSWORD_LENGTH];
        for (int k = 0; k < PASSWORD_LENGTH; k++) {
            password[k] = (char) random.nextInt(START_PASSWORD_CHAR, END_PASSWORD_CHAR);
        }
        return password;
    }

    @Override
    public String getPasswordHash(char[] password) {
        StringBuilder builder = new StringBuilder();
        builder.append(password);
        for (int k = 0; k < builder.length(); k++) {
            char c = (char) (builder.charAt(k) + SALT_OFFSET);
            builder.setCharAt(k, c);
        }
        return passwordEncoder.encode(builder);
    }

}
