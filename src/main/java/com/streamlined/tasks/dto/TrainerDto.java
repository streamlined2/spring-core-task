package com.streamlined.tasks.dto;

import java.util.Objects;

public record TrainerDto(Long userId, String firstName, String lastName, String userName, boolean isActive,
        String specialization) {

    @Override
    public String toString() {
        return "TrainerDto{userId=%d}".formatted(userId());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TrainerDto trainer) {
            return Objects.equals(userId(), trainer.userId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId());
    }

}
