package com.streamlined.tasks.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import com.streamlined.tasks.dto.TrainingDto;
import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.TrainingType;

class TrainingMapperTest {

    private TrainingMapper trainingMapper = new TrainingMapper();

    @Test
    void toEntityShouldReturnInstanceOfTrainingEntity_ifSucceeds() {
        Long traineeId = 1L;
        Long trainerId = 2L;
        String name = "Math";
        TrainingType trainingType = new TrainingType("Math");
        LocalDate date = LocalDate.of(2000, 1, 1);
        Duration duration = Duration.of(10, ChronoUnit.DAYS);
        TrainingDto dto = new TrainingDto(traineeId, trainerId, name, trainingType, date, duration);

        Training entity = trainingMapper.toEntity(dto);

        assertEquals(traineeId, entity.getTraineeId());
        assertEquals(trainerId, entity.getTrainerId());
        assertEquals(name, entity.getName());
        assertEquals(trainingType, entity.getType());
        assertEquals(date, entity.getDate());
        assertEquals(duration, entity.getDuration());
    }

    @Test
    void toDtoShouldReturnInstanceOfTrainingDto_ifSucceeds() {
        Long traineeId = 1L;
        Long trainerId = 2L;
        String name = "Math";
        TrainingType trainingType = new TrainingType("Math");
        LocalDate date = LocalDate.of(2000, 1, 1);
        Duration duration = Duration.of(10, ChronoUnit.DAYS);
        Training entity = new Training(traineeId, trainerId, name, trainingType, date, duration);

        TrainingDto dto = trainingMapper.toDto(entity);

        assertEquals(traineeId, dto.traineeId());
        assertEquals(trainerId, dto.trainerId());
        assertEquals(name, dto.name());
        assertEquals(trainingType, dto.type());
        assertEquals(date, dto.date());
        assertEquals(duration, dto.duration());
    }

}
