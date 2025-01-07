package com.streamlined.tasks.dto;

import java.time.LocalDate;
import java.util.Objects;

public record TraineeDto(Long userId, String firstName, String lastName, String userName, boolean isActive,
        LocalDate dateOfBirth, String address) {

    @Override
    public String toString() {
        return "TraineeDto{userId=%d}".formatted(userId());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TraineeDto trainee) {
            return Objects.equals(userId(), trainee.userId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId());
    }

}
