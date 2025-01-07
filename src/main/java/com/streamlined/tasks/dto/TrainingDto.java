package com.streamlined.tasks.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

import com.streamlined.tasks.entity.TrainingType;

public record TrainingDto(Long traineeId, Long trainerId, String name, TrainingType type, LocalDate date,
        Duration duration) {

    @Override
    public String toString() {
        return "TrainingDto{traineeId=%d, trainerId=%d, name=%s, type=%s, date=%tF, duration=%s}".formatted(traineeId(),
                trainerId(), name(), type.getName(), date(), duration.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TrainingDto training) {
            return Objects.equals(traineeId(), training.traineeId())
                    && Objects.equals(trainerId(), training.trainerId()) && Objects.equals(date(), training.date());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(traineeId(), trainerId(), date());
    }

}
