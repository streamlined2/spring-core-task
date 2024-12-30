package com.streamlined.tasks.dto;

import java.time.Duration;
import java.time.LocalDate;

import com.streamlined.tasks.entity.TrainingType;

public record TrainingDto(Long traineeId, Long trainerId, String name, TrainingType type, LocalDate date,
        Duration duration) {

}
