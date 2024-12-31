package com.streamlined.tasks.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.storage.HashMapStorage;

@ExtendWith(MockitoExtension.class)
class HashMapTraineeRepositoryTest {

    @Spy
    private HashMapStorage<Long, Trainee> traineeStorage;

    @InjectMocks
    private HashMapTraineeRepository traineeRepository;

    @Test
    @DisplayName("create should create new trainee entity if entity with such id does not exist")
    void testCreate_shouldCreateNewTrainee_ifTraineeEntityWithSuchIdDoesNotExist() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");

        traineeRepository.create(trainee);

        assertEquals(1, traineeStorage.size());
        Trainee savedTrainee = traineeStorage.get(trainee.getUserId());
        assertNotNull(savedTrainee);
        assertEquals(trainee.toString(), savedTrainee.toString());
    }

    @Test
    @DisplayName("create should throw exception if entity with such id exists")
    void testCreate_shouldThrowException_ifTraineeEntityWithSuchIdExists() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee);

        assertThrows(EntityAlreadyExistsException.class, () -> traineeRepository.create(trainee));
        assertEquals(1, traineeStorage.size());
        Trainee savedTrainee = traineeStorage.get(trainee.getUserId());
        assertNotNull(savedTrainee);
        assertEquals(trainee.toString(), savedTrainee.toString());
    }

    @Test
    @DisplayName("update should update trainee entity data if entity with such id exists")
    void testUpdate_shouldUpdateTraineeEntityData_ifTraineeEntityWithSuchIdExists() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee);
        Trainee updatedTrainee = new Trainee(5L, "Fred", "Smith", "Fred.Smith", "", true, LocalDate.of(1998, 6, 28),
                "UK");

        traineeRepository.update(updatedTrainee);

        assertEquals(1, traineeStorage.size());
        Trainee savedTrainee = traineeStorage.get(trainee.getUserId());
        assertNotNull(savedTrainee);
        assertEquals(updatedTrainee.toString(), savedTrainee.toString());
    }

    @Test
    @DisplayName("update should add new trainee entity if entity with such id does not exist")
    void testUpdate_shouldAddNewTraineeEntity_ifTraineeEntityWithSuchIdDoesNotExist() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee);
        Trainee updatedTrainee = new Trainee(6L, "Fred", "Smith", "Fred.Smith", "", true, LocalDate.of(1998, 6, 28),
                "UK");

        traineeRepository.update(updatedTrainee);

        assertEquals(2, traineeStorage.size());
        Trainee savedTrainee = traineeStorage.get(trainee.getUserId());
        assertNotNull(savedTrainee);
        assertEquals(trainee.toString(), savedTrainee.toString());
        savedTrainee = traineeStorage.get(updatedTrainee.getUserId());
        assertNotNull(savedTrainee);
        assertEquals(updatedTrainee.toString(), savedTrainee.toString());
    }

    @Test
    @DisplayName("deleteById should delete trainee entity if entity with such id exists")
    void testDeleteById_shouldDeleteTraineeEntity_ifTraineeEntityWithSuchIdExists() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee);

        traineeRepository.deleteById(trainee.getUserId());

        assertEquals(0, traineeStorage.size());
    }

    @Test
    @DisplayName("deleteById should throw exception if entity with such id does not exist")
    void testDeleteById_shouldThrowException_ifTraineeEntityWithSuchIdDoesNotExist() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee);
        Trainee traineeToBeDeleted = new Trainee(6L, "Fred", "Smith", "Fred.Smith", "", true, LocalDate.of(1998, 6, 28),
                "UK");

        assertThrows(NoSuchEntityException.class, () -> traineeRepository.deleteById(traineeToBeDeleted.getUserId()));

        assertEquals(1, traineeStorage.size());
        Trainee savedTrainee = traineeStorage.get(trainee.getUserId());
        assertNotNull(savedTrainee);
        assertEquals(trainee.toString(), savedTrainee.toString());
    }

    @Test
    @DisplayName("findById should return trainee entity wrapped in Optional if entity with such id exists")
    void testFindById_shouldReturnTraineeEntity_ifTraineeEntityWithSuchIdExists() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee);

        Optional<Trainee> foundTrainee = traineeRepository.findById(trainee.getUserId());

        assertTrue(foundTrainee.isPresent());
        assertSame(trainee, foundTrainee.get());
    }

    @Test
    @DisplayName("findById should return empty Optional if entity with such id does not exist")
    void testFindById_shouldReturnEmptyOptional_ifTraineeEntityWithSuchIdDoesNotExist() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee);
        Trainee traineeToLookup = new Trainee(6L, "Fred", "Smith", "Fred.Smith", "", true, LocalDate.of(1998, 6, 28),
                "UK");

        Optional<Trainee> foundTrainee = traineeRepository.findById(traineeToLookup.getUserId());

        assertTrue(foundTrainee.isEmpty());
    }

    @Test
    @DisplayName("findAll should return all trainee entities storage contains")
    void testFindAll_shouldReturnAllTraineeEntitiesStorageContains() {
        traineeStorage.clear();
        Trainee trainee1 = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee1);
        Trainee trainee2 = new Trainee(6L, "Fred", "Smith", "Fred.Smith", "", true, LocalDate.of(1998, 6, 28), "UK");
        traineeStorage.saveNew(trainee2);

        List<Trainee> foundTrainees = traineeRepository.findAll().toList();

        assertEquals(2, foundTrainees.size());
        Trainee foundTrainee = traineeStorage.get(trainee1.getUserId());
        assertNotNull(foundTrainee);
        assertEquals(trainee1.toString(), foundTrainee.toString());
        foundTrainee = traineeStorage.get(trainee2.getUserId());
        assertNotNull(foundTrainee);
        assertEquals(trainee2.toString(), foundTrainee.toString());
    }

    @Test
    @DisplayName("getMaxUsernameSerial should return empty optional if given user name does not exist in storage")
    void testGetMaxUsernameSerial_shouldReturnEmptyOptional_ifGivenUserNameDoesNotExistInStorage() {
        traineeStorage.clear();

        assertTrue(traineeRepository.getMaxUsernameSerial("Kyle", "Stark").isEmpty());
    }

    @Test
    @DisplayName("getMaxUsernameSerial should return empty string if given user name present in storage once")
    void testGetMaxUsernameSerial_shouldReturnEmptyString_ifGivenUserNamePresentInStorageOnce() {
        traineeStorage.clear();
        Trainee trainee = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee);

        Optional<String> serialNumber = traineeRepository.getMaxUsernameSerial("Kyle", "Stark");
        assertTrue(serialNumber.isPresent());
        assertTrue(serialNumber.get().isEmpty());
    }

    @Test
    @DisplayName("getMaxUsernameSerial should return 1 if given user name present in storage twice")
    void testGetMaxUsernameSerial_shouldReturn1_ifGivenUserNamePresentInStorageTwice() {
        traineeStorage.clear();
        Trainee trainee1 = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee1);
        Trainee trainee2 = new Trainee(6L, "Kyle", "Stark", "Kyle.Stark1", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee2);

        Optional<String> serialNumber = traineeRepository.getMaxUsernameSerial("Kyle", "Stark");
        assertTrue(serialNumber.isPresent());
        assertEquals(1, Long.valueOf(serialNumber.get()));
    }

    @Test
    @DisplayName("getMaxUsernameSerial should return 2 if given user name present in storage thrice")
    void testGetMaxUsernameSerial_shouldReturn2_ifGivenUserNamePresentInStorageThrice() {
        traineeStorage.clear();
        Trainee trainee1 = new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee1);
        Trainee trainee2 = new Trainee(6L, "Kyle", "Stark", "Kyle.Stark1", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee2);
        Trainee trainee3 = new Trainee(7L, "Kyle", "Stark", "Kyle.Stark2", "", true, LocalDate.of(1988, 5, 18), "USA");
        traineeStorage.saveNew(trainee3);

        Optional<String> serialNumber = traineeRepository.getMaxUsernameSerial("Kyle", "Stark");
        assertTrue(serialNumber.isPresent());
        assertEquals(2, Long.valueOf(serialNumber.get()));
    }

}
