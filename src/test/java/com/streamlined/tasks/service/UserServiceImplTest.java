package com.streamlined.tasks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.entity.User;
import com.streamlined.tasks.repository.TraineeRepository;
import com.streamlined.tasks.repository.TrainerRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private TrainerRepository trainerRepository = mock(TrainerRepository.class);
    private TraineeRepository traineeRepository = mock(TraineeRepository.class);

    private UserServiceImpl userService = new UserServiceImpl() {
    };

    @BeforeEach
    void setup() {
        userService.setTraineeRepository(traineeRepository);
        userService.setTrainerRepository(trainerRepository);
    }

    @Test
    void setNextUsernameSerialShouldSetBlankSerial_ifNoSuchUserExists() {
        when(trainerRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.empty());
        when(traineeRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.empty());

        User user = new Trainee(0L, "Tingrid", "Kim", "", "", false, LocalDate.of(1999, 8, 12), "Sweden");
        userService.setNextUsernameSerial(user);

        assertNotNull(user.getUsernameSerial());
        assertTrue(user.getUsernameSerial().isEmpty());
        assertEquals("Tingrid.Kim", user.getUserName());
    }

    @Test
    void setNextUsernameSerialShouldSetSerial1_ifOneSuchUserExistsInTrainerRepository() {
        when(trainerRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of(""));
        when(traineeRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.empty());

        User user = new Trainee(0L, "Tingrid", "Kim", "", "", false, LocalDate.of(1999, 8, 12), "Sweden");
        userService.setNextUsernameSerial(user);

        assertNotNull(user.getUsernameSerial());
        assertEquals("1", user.getUsernameSerial());
        assertEquals("Tingrid.Kim1", user.getUserName());
    }

    @Test
    void setNextUsernameSerialShouldSetSerial1_ifOneSuchUserExistsInTraineeRepository() {
        when(trainerRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.empty());
        when(traineeRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of(""));

        User user = new Trainee(0L, "Tingrid", "Kim", "", "", false, LocalDate.of(1999, 8, 12), "Sweden");
        userService.setNextUsernameSerial(user);

        assertNotNull(user.getUsernameSerial());
        assertEquals("1", user.getUsernameSerial());
        assertEquals("Tingrid.Kim1", user.getUserName());
    }

    @Test
    void setNextUsernameSerialShouldSetSerial2_ifOneSuchUserExistsInEachTraineeAndTrainerRepository() {
        when(trainerRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of(""));
        when(traineeRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of("1"));

        User user = new Trainee(0L, "Tingrid", "Kim", "", "", false, LocalDate.of(1999, 8, 12), "Sweden");
        userService.setNextUsernameSerial(user);

        assertNotNull(user.getUsernameSerial());
        assertEquals("2", user.getUsernameSerial());
        assertEquals("Tingrid.Kim2", user.getUserName());
    }

    @Test
    void setNextUsernameSerialShouldSetSerial2_ifOneSuchUserExistsInEachTrainerAndTraineeRepository() {
        when(trainerRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of("1"));
        when(traineeRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of(""));

        User user = new Trainee(0L, "Tingrid", "Kim", "", "", false, LocalDate.of(1999, 8, 12), "Sweden");
        userService.setNextUsernameSerial(user);

        assertNotNull(user.getUsernameSerial());
        assertEquals("2", user.getUsernameSerial());
        assertEquals("Tingrid.Kim2", user.getUserName());
    }

    @Test
    void setNextUsernameSerialShouldSetSerialToNextMaxValue_ifSeveralSuchUsersExistInTrainerAndTraineeRepositories() {
        when(trainerRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of("5"));
        when(traineeRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of("10"));

        User user = new Trainee(0L, "Tingrid", "Kim", "", "", false, LocalDate.of(1999, 8, 12), "Sweden");
        userService.setNextUsernameSerial(user);

        assertNotNull(user.getUsernameSerial());
        assertEquals("11", user.getUsernameSerial());
        assertEquals("Tingrid.Kim11", user.getUserName());
    }

    @Test
    void setNextUsernameSerialShouldSetSerialToNextMaxValue_ifSeveralSuchUsersExistInTraineeAndTrainerRepositories() {
        when(trainerRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of("10"));
        when(traineeRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of("20"));

        User user = new Trainee(0L, "Tingrid", "Kim", "", "", false, LocalDate.of(1999, 8, 12), "Sweden");
        userService.setNextUsernameSerial(user);

        assertNotNull(user.getUsernameSerial());
        assertEquals("21", user.getUsernameSerial());
        assertEquals("Tingrid.Kim21", user.getUserName());
    }

    @Test
    void setNextUsernameSerialShouldSetSerialToNextMaxValue_ifSameNumberOfUsersExistInTraineeAndTrainerRepositories() {
        when(trainerRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of("10"));
        when(traineeRepository.getMaxUsernameSerial(anyString(), anyString())).thenReturn(Optional.of("10"));

        User user = new Trainee(0L, "Tingrid", "Kim", "", "", false, LocalDate.of(1999, 8, 12), "Sweden");
        userService.setNextUsernameSerial(user);

        assertNotNull(user.getUsernameSerial());
        assertEquals("11", user.getUsernameSerial());
        assertEquals("Tingrid.Kim11", user.getUserName());
    }

}
