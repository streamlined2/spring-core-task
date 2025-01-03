package com.streamlined.tasks;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.streamlined.tasks.exception.MissingAlgorithmException;

@Configuration
public class SecurityConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    private @Value("${algorithm.random}") String algorithmName;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptVersion.$2Y);
    }

    @Bean
    Random random() {
        try {
            return SecureRandom.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Missing random generator algorithm {}", algorithmName, e);
            throw new MissingAlgorithmException("Missing random generator algorithm", e);
        }
    }

}
