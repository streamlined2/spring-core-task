package com.streamlined.tasks.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.entity.Trainee;

class TraineeMapperTest {

    private TraineeMapper traineeMapper = new TraineeMapper();

    @Test
    void toEntityShouldReturnInstanceOfTraineeEntity_ifSucceeds() {
        Long userId = 1L;
        String firstName = "Jack";
        String lastName = "Powell";
        String userName = "Jack.Powell";
        boolean isActive = true;
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        String address = "UK";
        TraineeDto dto = new TraineeDto(userId, firstName, lastName, userName, isActive, dateOfBirth, address);

        Trainee entity = traineeMapper.toEntity(dto);

        assertEquals(userId, entity.getUserId());
        assertEquals(firstName, entity.getFirstName());
        assertEquals(lastName, entity.getLastName());
        assertEquals(userName, entity.getUserName());
        assertEquals(isActive, entity.isActive());
        assertEquals(dateOfBirth, entity.getDateOfBirth());
        assertEquals(address, entity.getAddress());
    }

    @Test
    void toDtoShouldReturnInstanceOfTraineeDto_ifSucceeds() {
        Long userId = 1L;
        String firstName = "Jack";
        String lastName = "Powell";
        String userName = "Jack.Powell";
        boolean isActive = true;
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        String address = "UK";
        Trainee entity = new Trainee(userId, firstName, lastName, userName, isActive, dateOfBirth, address);

        TraineeDto dto = traineeMapper.toDto(entity);

        assertEquals(userId, dto.userId());
        assertEquals(firstName, dto.firstName());
        assertEquals(lastName, dto.lastName());
        assertEquals(userName, dto.userName());
        assertEquals(isActive, dto.isActive());
        assertEquals(dateOfBirth, dto.dateOfBirth());
        assertEquals(address, dto.address());
    }

}
