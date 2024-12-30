package com.streamlined.tasks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityDeletionException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.exception.EntityUpdateException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.mapper.TraineeMapper;
import com.streamlined.tasks.repository.TraineeRepository;

@ExtendWith(MockitoExtension.class)
class DefaultTraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Spy
    private TraineeMapper traineeMapper;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private DefaultTraineeService traineeService;

    @Test
    @DisplayName("create adds new entity")
    void testCreate_shouldCreateNewEntity_ifCreateSucceeds() {
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        TraineeDto traineeDto = traineeMapper.toDto(trainee);
        final char[] password = "password".toCharArray();
        List<Trainee> traineeList = new ArrayList<>(List.of(
                new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA"),
                new Trainee(2L, "Jack", "Powell", "Jack.Powell", "", true, LocalDate.of(1992, 2, 15), "UK"),
                new Trainee(3L, "Robert", "Orwell", "Robert.Orwell", "", true, LocalDate.of(1991, 3, 10), "UK"),
                new Trainee(4L, "Tingrid", "Kim", "Tingrid.Kim", "", false, LocalDate.of(1999, 8, 12), "Sweden")));
        final int initialTraineeListSize = traineeList.size();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                traineeList.add(trainee);
                return null;
            }
        }).when(traineeRepository).create(trainee);

        traineeService.create(traineeDto, password);

        assertEquals(initialTraineeListSize + 1, traineeList.size());
        assertTrue(traineeList.contains(trainee));
        Optional<Trainee> newTrainee = traineeList.stream().filter(t -> t.getUserId().equals(trainee.getUserId()))
                .findFirst();
        assertTrue(newTrainee.isPresent());
        assertEquals(traineeDto, traineeMapper.toDto(newTrainee.get()));
        verify(traineeRepository).create(trainee);
    }

    @Test
    @DisplayName("create throws exception if trainee repository throws exception")
    void testCreate_shouldThrowEntityCreationException_ifTraineeRepositoryThrowsException() {
        Trainee trainee = new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA");
        TraineeDto traineeDto = traineeMapper.toDto(trainee);
        final String errorMessage = "exception prevented new trainee entity creation";
        final char[] password = "password".toCharArray();

        doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(traineeRepository).create(trainee);

        Exception e = assertThrows(EntityCreationException.class, () -> traineeService.create(traineeDto, password));
        assertEquals("Error creating trainee entity", e.getMessage());
        assertEquals(errorMessage, e.getCause().getMessage());
        verify(traineeRepository).create(trainee);
    }

    @Test
    @DisplayName("update updates entity data for given trainee entity")
    void testUpdate_shouldUpdateEntityData_ifTraineeFoundAndUpdateSucceeds() {
        final int traineeIndex = 3;
        Trainee trainee = new Trainee(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, LocalDate.of(1989, 4, 12), "Norway");
        TraineeDto traineeDto = traineeMapper.toDto(trainee);
        List<Trainee> traineeList = new ArrayList<>(
                List.of(new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA"),
                        new Trainee(2L, "Jack", "Powell", "Jack.Powell", "", true, LocalDate.of(1992, 2, 15), "UK"),
                        new Trainee(3L, "Robert", "Orwell", "Robert.Orwell", "", true, LocalDate.of(1991, 3, 10), "UK"),
                        new Trainee(4L, "Tingrid", "Kim", "Tingrid.Kim", "", false, LocalDate.of(1999, 8, 12),
                                "Sweden"),
                        new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA")));

        when(traineeRepository.findById(trainee.getUserId())).thenReturn(Optional.of(trainee));
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                traineeList.set(traineeIndex, trainee);
                return null;
            }
        }).when(traineeRepository).update(trainee);

        traineeService.update(traineeDto);

        assertEquals(traineeDto, traineeMapper.toDto(traineeList.get(traineeIndex)));
        verify(traineeRepository).findById(trainee.getUserId());
        verify(traineeRepository).update(trainee);
    }

    @Test
    @DisplayName("update throws exception if trainee repository throws exception")
    void testUpdate_shouldThrowEntityUpdateException_ifTraineeRepositoryThrowsException() {
        Trainee trainee = new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA");
        TraineeDto traineeDto = traineeMapper.toDto(trainee);
        final String errorMessage = "exception prevented update";

        when(traineeRepository.findById(trainee.getUserId())).thenReturn(Optional.of(trainee));
        doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(traineeRepository).update(trainee);

        Exception e = assertThrows(EntityUpdateException.class, () -> traineeService.update(traineeDto));
        assertEquals("Error updating trainee entity", e.getMessage());
        assertEquals(errorMessage, e.getCause().getMessage());
        verify(traineeRepository).findById(trainee.getUserId());
        verify(traineeRepository).update(trainee);
    }

    @Test
    @DisplayName("update throws exception if trainee not found")
    void testUpdate_shouldThrowEntityUpdateException_ifTraineeNotFound() {
        Trainee trainee = new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA");
        TraineeDto traineeDto = traineeMapper.toDto(trainee);
        final String errorMessage = "No trainee entity with id %d".formatted(trainee.getUserId());

        when(traineeRepository.findById(trainee.getUserId())).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityUpdateException.class, () -> traineeService.update(traineeDto));
        assertEquals("Error updating trainee entity", e.getMessage());
        assertEquals(errorMessage, e.getCause().getMessage());
        verify(traineeRepository).findById(trainee.getUserId());
        verify(traineeRepository, never()).update(trainee);
    }

    @Test
    @DisplayName("updatePassword updates password for given trainee entity")
    void testUpdatePassword_shouldUpdatePassword_ifTraineeFoundAndUpdateSucceeds() {
        final int traineeIndex = 3;
        Trainee trainee = new Trainee(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, LocalDate.of(1989, 4, 12), "Norway");
        List<Trainee> traineeList = new ArrayList<>(
                List.of(new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA"),
                        new Trainee(2L, "Jack", "Powell", "Jack.Powell", "", true, LocalDate.of(1992, 2, 15), "UK"),
                        new Trainee(3L, "Robert", "Orwell", "Robert.Orwell", "", true, LocalDate.of(1991, 3, 10), "UK"),
                        new Trainee(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, LocalDate.of(1989, 4, 12), "Norway"),
                        new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA")));
        final String password = "password";

        when(traineeRepository.findById(trainee.getUserId())).thenReturn(Optional.of(trainee));
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Trainee foundTrainee = traineeList.get(traineeIndex);
                foundTrainee.setPasswordHash(password);
                return null;
            }
        }).when(traineeRepository).update(trainee);

        traineeService.updatePassword(trainee.getUserId(), password.toCharArray());

        assertEquals(password, traineeList.get(traineeIndex).getPasswordHash());
        verify(traineeRepository).findById(trainee.getUserId());
        verify(traineeRepository).update(trainee);
    }

    @Test
    @DisplayName("updatePassword throws exception if trainee repository throws exception")
    void testUpdatePassword_shouldThrowEntityUpdateException_ifTraineeRepositoryThrowsException() {
        Trainee trainee = new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA");
        final String errorMessage = "exception prevented password update";
        final char[] password = "password".toCharArray();

        when(traineeRepository.findById(trainee.getUserId())).thenReturn(Optional.of(trainee));
        doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(traineeRepository).update(trainee);

        Exception e = assertThrows(EntityUpdateException.class,
                () -> traineeService.updatePassword(trainee.getUserId(), password));
        assertEquals("Error updating password for trainee entity", e.getMessage());
        assertEquals(errorMessage, e.getCause().getMessage());
        verify(traineeRepository).findById(trainee.getUserId());
        verify(traineeRepository).update(trainee);
    }

    @Test
    @DisplayName("updatePassword throws exception if trainee not found")
    void testUpdatePassword_shouldThrowEntityUpdateException_ifTraineeNotFound() {
        Trainee trainee = new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA");
        final String errorMessage = "No trainee entity with id %d".formatted(trainee.getUserId());
        final char[] password = "password".toCharArray();

        when(traineeRepository.findById(trainee.getUserId())).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityUpdateException.class,
                () -> traineeService.updatePassword(trainee.getUserId(), password));
        assertEquals("Error updating password for trainee entity", e.getMessage());
        assertEquals(errorMessage, e.getCause().getMessage());
        verify(traineeRepository).findById(trainee.getUserId());
        verify(traineeRepository, never()).update(trainee);
    }

    @Test
    @DisplayName("deleteById deletes entity if trainee with given id exists")
    void testDeleteById_shouldDeleteTraineeEntity_ifTraineeWithGivenIdExists() {
        Trainee trainee = new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA");
        List<Trainee> traineeList = new ArrayList<>(
                List.of(new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA"),
                        new Trainee(2L, "Jack", "Powell", "Jack.Powell", "", true, LocalDate.of(1992, 2, 15), "UK"),
                        new Trainee(3L, "Robert", "Orwell", "Robert.Orwell", "", true, LocalDate.of(1991, 3, 10), "UK"),
                        new Trainee(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, LocalDate.of(1989, 4, 12), "Norway"),
                        new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA")));
        final int initialListSize = traineeList.size();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                traineeList.remove(trainee);
                return null;
            }
        }).when(traineeRepository).deleteById(trainee.getUserId());

        traineeService.deleteById(trainee.getUserId());

        assertFalse(traineeList.contains(trainee));
        assertEquals(initialListSize - 1, traineeList.size());
        verify(traineeRepository).deleteById(trainee.getUserId());
    }

    @Test
    @DisplayName("deleteById throws exception if trainee with given id does not exist")
    void testDeleteById_shouldThrowEntityDeletionException_ifTraineeWithGivenIdDoesNotExist() {
        Long nonExistentId = 1L;
        doAnswer(new ThrowsException(
                new NoSuchEntityException("No entity found with id %d".formatted(nonExistentId.intValue()))))
                .when(traineeRepository).deleteById(nonExistentId);

        Exception e = assertThrows(EntityDeletionException.class, () -> traineeService.deleteById(nonExistentId));
        assertEquals("Error deleting trainee entity", e.getMessage());
        assertEquals("No entity found with id %d".formatted(nonExistentId.intValue()), e.getCause().getMessage());
        verify(traineeRepository).deleteById(nonExistentId);
    }

    @Test
    @DisplayName("deleteById throws exception if trainee repository throws exception")
    void testDeleteById_shouldThrowEntityDeletionException_ifTraineeRepositoryThrowsException() {
        Long nonExistentId = 1L;
        String errorMessage = "cannot delete entity";
        doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(traineeRepository)
                .deleteById(nonExistentId);

        Exception e = assertThrows(EntityDeletionException.class, () -> traineeService.deleteById(nonExistentId));
        assertEquals("Error deleting trainee entity", e.getMessage());
        assertEquals(errorMessage, e.getCause().getMessage());
        verify(traineeRepository).deleteById(nonExistentId);
    }

    @Test
    @DisplayName("findById fetches trainee entity for given id")
    void testFindById_shouldReturnFoundTraineeEntity_ifPassedIdValidAndExists() {
        Long traineeId = 3L;
        Trainee expectedTrainee = new Trainee(traineeId, "Robert", "Orwell", "Robert.Orwell", "", true,
                LocalDate.of(1991, 3, 10), "UK");
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(expectedTrainee));

        Optional<TraineeDto> actualTraineeDto = traineeService.findById(traineeId);
        TraineeDto expectedTraineeDto = traineeMapper.toDto(expectedTrainee);

        verify(traineeRepository).findById(traineeId);
        assertTrue(actualTraineeDto.isPresent());
        assertEquals(expectedTraineeDto, actualTraineeDto.get());
    }

    @Test
    @DisplayName("findById returns empty Optional if given id does not exist")
    void testFindById_shouldReturnEmptyOptional_ifPassedIdDoesNotExist() {
        Long traineeId = 1L;
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.empty());

        Optional<TraineeDto> actualTraineeDto = traineeService.findById(traineeId);

        verify(traineeRepository).findById(traineeId);
        assertTrue(actualTraineeDto.isEmpty());
    }

    @Test
    @DisplayName("findById throws exception if trainee repository throws exception")
    void testFindById_shouldThrowEntityQueryException_ifTraineeRepositoryThrowsException() {
        Long nonExistentId = 1L;
        when(traineeRepository.findById(anyLong())).thenThrow(EntityQueryException.class);

        assertThrows(EntityQueryException.class, () -> traineeService.findById(nonExistentId));
        verify(traineeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("findAll fetches all available trainee entities")
    void testFindAll_shouldReturnAllAvailableTraineeEntities_ifTraineeRepositoryReturnsResult() {
        List<Trainee> expectedTraineeList = List.of(
                new Trainee(1L, "John", "Smith", "John.Smith", "", true, LocalDate.of(1990, 1, 1), "USA"),
                new Trainee(2L, "Jack", "Powell", "Jack.Powell", "", true, LocalDate.of(1992, 2, 15), "UK"),
                new Trainee(3L, "Robert", "Orwell", "Robert.Orwell", "", true, LocalDate.of(1991, 3, 10), "UK"),
                new Trainee(4L, "Ingrid", "Kin", "Ingrid.Kin", "", true, LocalDate.of(1989, 4, 12), "Norway"),
                new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA"));

        when(traineeRepository.findAll()).thenReturn(expectedTraineeList.stream());

        List<TraineeDto> resultingDtoList = traineeService.findAll().toList();
        List<TraineeDto> expectedDtoList = expectedTraineeList.stream().map(traineeMapper::toDto).toList();

        assertEquals(expectedDtoList, resultingDtoList);
        verify(traineeRepository).findAll();
    }

    @Test
    @DisplayName("findAll throws exception if trainee repository throws exception")
    void testFindAll_shouldThrowEntityQueryException_ifTraineeRepositoryThrowsException() {
        when(traineeRepository.findAll()).thenThrow(RuntimeException.class);

        assertThrows(EntityQueryException.class, () -> traineeService.findAll());
        verify(traineeRepository).findAll();
    }

}
