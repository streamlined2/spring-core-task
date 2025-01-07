package com.streamlined.tasks.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Trainee extends User {

    private LocalDate dateOfBirth;
    private String address;

    public Trainee() {
    }

    public Trainee(Long userId, String firstName, String lastName, String userName, boolean isActive,
            LocalDate dateOfBirth, String address) {
        super(userId, firstName, lastName, userName, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Trainee(Long userId, String firstName, String lastName, String userName, String passwordHash,
            boolean isActive, LocalDate dateOfBirth, String address) {
        super(userId, firstName, lastName, userName, passwordHash, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Trainee{userId=%d}".formatted(getUserId());
    }

    @Override
    public boolean isIdenticalTo(Entity<Long> entity) {
        if (entity instanceof Trainee trainee) {
            return Objects.equals(getUserId(), trainee.getUserId())
                    && Objects.equals(getFirstName(), trainee.getFirstName())
                    && Objects.equals(getLastName(), trainee.getLastName())
                    && Objects.equals(getUserName(), trainee.getUserName())
                    && Objects.equals(isActive(), trainee.isActive())
                    && Objects.equals(getDateOfBirth(), trainee.getDateOfBirth())
                    && Objects.equals(getAddress(), trainee.getAddress());
        }
        return false;
    }

}
