package com.streamlined.tasks.entity;

import java.util.Objects;

public abstract class User implements Entity<Long> {

    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String passwordHash;
    private boolean isActive;

    protected User() {
    }

    protected User(Long userId, String firstName, String lastName, String userName, boolean isActive) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isActive = isActive;
    }

    protected User(Long userId, String firstName, String lastName, String userName, String passwordHash,
            boolean isActive) {
        this(userId, firstName, lastName, userName, isActive);
        this.passwordHash = passwordHash;
    }

    public boolean userNameStartsWith(String firstName, String lastName) {
        return getUserName().startsWith(getInitialUsername(firstName, lastName));
    }

    private String getInitialUsername(String firstName, String lastName) {
        return firstName + "." + lastName;
    }

    public String getUsernameSerial() {
        return getUserName().substring(getInitialUsername(firstName, lastName).length());
    }

    public void setUsernameSerial(String serial) {
        userName = getInitialUsername(firstName, lastName) + serial;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User user) {
            return Objects.equals(userId, user.userId);
        }
        return false;
    }

    @Override
    public Long getPrimaryKey() {
        return getUserId();
    }

}
