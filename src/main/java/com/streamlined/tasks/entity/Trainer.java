package com.streamlined.tasks.entity;

import java.util.Objects;

public class Trainer extends User {

    private String specialization;

    public Trainer() {
    }

    public Trainer(Long userId, String firstName, String lastName, String userName, boolean isActive,
            String specialization) {
        super(userId, firstName, lastName, userName, isActive);
        this.specialization = specialization;
    }

    public Trainer(Long userId, String firstName, String lastName, String userName, String passwordHash,
            boolean isActive, String specialization) {
        super(userId, firstName, lastName, userName, passwordHash, isActive);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Trainer{userId=%d}".formatted(getUserId());
    }

    @Override
    public boolean isIdenticalTo(Entity<Long> entity) {
        if (entity instanceof Trainer trainer) {
            return Objects.equals(getUserId(), trainer.getUserId())
                    && Objects.equals(getFirstName(), trainer.getFirstName())
                    && Objects.equals(getLastName(), trainer.getLastName())
                    && Objects.equals(getUserName(), trainer.getUserName())
                    && Objects.equals(isActive(), trainer.isActive())
                    && Objects.equals(getSpecialization(), trainer.getSpecialization());
        }
        return false;
    }

}
