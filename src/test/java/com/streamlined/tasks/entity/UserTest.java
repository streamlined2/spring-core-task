package com.streamlined.tasks.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void userNameStartsWithShouldReturnTrue_ifSucceeds() {
        User user = new Trainer(105L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");

        assertTrue(user.userNameStartsWith("Jack", "Fantasy"));
    }

    @Test
    void userNameStartsWithShouldReturnFalse_ifFalse() {
        User user = new Trainer(105L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");

        assertFalse(user.userNameStartsWith("Jack", "Welsh"));
        assertFalse(user.userNameStartsWith("John", "Fantasy"));
    }

    @Test
    void getUsernameSerialShouldReturn20_ifUsernameSerialNumberEqualsTo20() {
        User user = new Trainer(105L, "Jack", "Fantasy", "Jack.Fantasy20", true, "Math");

        assertEquals("20", user.getUsernameSerial());
    }

    @Test
    void setUsernameSerialShouldSetUsernameSerialNumberTo10_ifSucceeds() {
        User user = new Trainer(105L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");

        user.setUsernameSerial("10");

        assertEquals("Jack.Fantasy10", user.getUserName());
    }

}
