package com.streamlined.tasks.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.entity.Trainer;

class TrainerMapperTest {

    private static TrainerMapper trainerMapper;

    @BeforeAll
    static void setup() {
        trainerMapper = new TrainerMapper();
    }

    @Test
    void toEntityShouldReturnInstanceOfTrainerEntity_ifSucceeds() {
        Long userId = 1L;
        String firstName = "Jack";
        String lastName = "Powell";
        String userName = "Jack.Powell";
        boolean isActive = true;
        String specialization = "Math";
        TrainerDto dto = new TrainerDto(userId, firstName, lastName, userName, isActive, specialization);

        Trainer entity = trainerMapper.toEntity(dto);

        assertEquals(userId, entity.getUserId());
        assertEquals(firstName, entity.getFirstName());
        assertEquals(lastName, entity.getLastName());
        assertEquals(userName, entity.getUserName());
        assertEquals(isActive, entity.isActive());
        assertEquals(specialization, entity.getSpecialization());
    }

    @Test
    void toDtoShouldReturnInstanceOfTrainerDto_ifSucceeds() {
        Long userId = 1L;
        String firstName = "Jack";
        String lastName = "Powell";
        String userName = "Jack.Powell";
        boolean isActive = true;
        String specialization = "Math";
        Trainer entity = new Trainer(userId, firstName, lastName, userName, isActive, specialization);

        TrainerDto dto = trainerMapper.toDto(entity);

        assertEquals(userId, dto.userId());
        assertEquals(firstName, dto.firstName());
        assertEquals(lastName, dto.lastName());
        assertEquals(userName, dto.userName());
        assertEquals(isActive, dto.isActive());
        assertEquals(specialization, dto.specialization());
    }

}
