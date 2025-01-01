package com.streamlined.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.test.context.TestPropertySource;
import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.mapper.TraineeMapper;
import com.streamlined.tasks.service.TraineeService;
import com.streamlined.tasks.storage.HashMapStorage;

@TestPropertySource(locations = "classpath:application-integration-test.properties")
@SpringBootTest(classes = SpringcoretaskApplication.class, useMainMethod = UseMainMethod.NEVER)
class SpringcoretaskApplicationTests {

    @Autowired
    private TraineeService traineeService;
    @Autowired
    private TraineeMapper traineeMapper;
    @Autowired
    private HashMapStorage<Long, Trainee> traineeStorage;

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
