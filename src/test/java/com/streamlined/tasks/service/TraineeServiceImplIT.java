package com.streamlined.tasks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.streamlined.tasks.SpringcoretaskApplication;
import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.mapper.TraineeMapper;
import com.streamlined.tasks.storage.InMemoryStorage;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
@ContextConfiguration(classes = SpringcoretaskApplication.class)
class TraineeServiceImplIT {

    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TraineeMapper traineeMapper;
    @Autowired
    private InMemoryStorage<Long, Trainee> traineeStorage;

    @Test
    void findAllShouldReturnListOfAllTrainees_ifSucceeds() {

        List<TraineeDto> trainees = traineeService.findAll().toList();

        assertEquals(traineeStorage.getAll().map(traineeMapper::toDto).toList(), trainees);
    }

    @Test
    void findByIdShouldReturnTraineeEntity_ifGivenIdPresent() {
        final Long traineeId = 1L;
        Optional<TraineeDto> trainee = traineeService.findById(traineeId);

        assertTrue(trainee.isPresent());
        assertEquals(traineeMapper.toDto(traineeStorage.get(traineeId)), trainee.get());
    }

    @Test
    void findByIdShouldReturnEmptyOptional_ifGivenIdAbsent() {
        final Long traineeId = -1L;
        Optional<TraineeDto> trainee = traineeService.findById(traineeId);

        assertTrue(trainee.isEmpty());
    }

}
