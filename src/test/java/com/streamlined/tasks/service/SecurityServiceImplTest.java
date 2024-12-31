package com.streamlined.tasks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class SecurityServiceImplTest {

    private static final int PASSWORD_LENGTH = 10;
    private static final char START_PASSWORD_CHAR = '\u0021';
    private static final char END_PASSWORD_CHAR = '\u007F';
    private static final int SALT_OFFSET = 10;

    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private Random random = mock(Random.class);
    private SecurityServiceImpl securityService = new SecurityServiceImpl(passwordEncoder, random, PASSWORD_LENGTH,
            START_PASSWORD_CHAR, END_PASSWORD_CHAR, SALT_OFFSET);

    @Test
    void testGetNewPassword_shouldReturnGeneratedPassword_ifSucceeds() {
        final char[] expectedPassword = "ABCDEFGHIJ".toCharArray();

        when(random.nextInt(anyInt(), anyInt())).thenReturn((int) 'A', (int) 'B', (int) 'C', (int) 'D', (int) 'E',
                (int) 'F', (int) 'G', (int) 'H', (int) 'I', (int) 'J');

        char[] actualPassword = securityService.getNewPassword();

        assertArrayEquals(expectedPassword, actualPassword, "expected and actual passwords differ");
    }

    @ParameterizedTest
    @MethodSource("getPasswordHashTestData")
    void testGetPasswordHash_shouldReturnHashedAndSaltedPassword(String password, String expectedHash) {
        when(passwordEncoder.encode(any())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                StringBuilder password = invocation.getArgument(0);
                return password.toString();
            }
        });

        String actualHash = securityService.getPasswordHash(password.toCharArray());
        
        assertEquals(expectedHash, actualHash);
    }

    private static Stream<Arguments> getPasswordHashTestData() {
        return Stream.of(Arguments.of("ABC", "KLM"), Arguments.of("DEF", "NOP"), Arguments.of("GHI", "QRS"));
    }
}
