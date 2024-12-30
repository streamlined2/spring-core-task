package com.streamlined.tasks.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.TrainingType;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.storage.TrainingStorage;

@ExtendWith(MockitoExtension.class)
class DefaultTrainingRepositoryTest {

    @Spy
    private TrainingStorage trainingStorage;

    @InjectMocks
    private DefaultTrainingRepository trainingRepository;

    private static TrainingType mathType;
    private static TrainingType artType;
    private static TrainingType computerType;

    @BeforeAll
    static void setup() {
        mathType = new TrainingType("math");
        artType = new TrainingType("art");
        computerType = new TrainingType("computer");
    }

    @Test
    @DisplayName("create should create new training entity if entity with such id does not exist")
    void testCreate_shouldCreateNewTraining_ifTrainingEntityWithSuchIdDoesNotExist() {
        trainingStorage.clear();
        Training training = new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        trainingRepository.create(training);

        assertEquals(1, trainingStorage.size());
        Training savedTraining = trainingStorage.get(training.getTrainingKey());
        assertNotNull(savedTraining);
        assertEquals(training.toString(), savedTraining.toString());
    }

    @Test
    @DisplayName("create should throw exception if entity with such id exists")
    void testCreate_shouldThrowException_ifTrainingEntityWithSuchIdExists() {
        trainingStorage.clear();
        Training training = new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));
        trainingStorage.saveNew(training);

        assertThrows(EntityAlreadyExistsException.class, () -> trainingRepository.create(training));
        assertEquals(1, trainingStorage.size());
        Training savedTraining = trainingStorage.get(training.getTrainingKey());
        assertNotNull(savedTraining);
        assertEquals(training.toString(), savedTraining.toString());
    }

    @Test
    @DisplayName("findById should return training entity wrapped in Optional if entity with such id exists")
    void testFindById_shouldReturnTrainingEntity_ifTrainingEntityWithSuchIdExists() {
        trainingStorage.clear();
        Training training = new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));
        trainingStorage.saveNew(training);

        Optional<Training> foundTraining = trainingRepository.findById(training.getTrainingKey());

        assumeTrue(foundTraining.isPresent());
        assertSame(training, foundTraining.get());
    }

    @Test
    @DisplayName("findById should return empty Optional if entity with such id does not exist")
    void testFindById_shouldReturnEmptyOptional_ifTrainingEntityWithSuchIdDoesNotExist() {
        trainingStorage.clear();
        Training training = new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));
        trainingStorage.saveNew(training);
        Training trainingToLookup = new Training(2L, 1L, "Math training", mathType, LocalDate.of(2021, 1, 1),
                Duration.ofDays(20));

        Optional<Training> foundTraining = trainingRepository.findById(trainingToLookup.getTrainingKey());

        assertTrue(foundTraining.isEmpty());
    }

    @Test
    @DisplayName("findAll should return all training entities storage contains")
    void testFindAll_shouldReturnAllTrainingEntitiesStorageContains() {
        trainingStorage.clear();
        Training training1 = new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));
        trainingStorage.saveNew(training1);
        Training training2 = new Training(2L, 1L, "Math training", mathType, LocalDate.of(2021, 1, 1),
                Duration.ofDays(20));
        trainingStorage.saveNew(training2);

        List<Training> foundTrainings = trainingRepository.findAll().toList();

        assertEquals(2, foundTrainings.size());
        Training foundTraining = trainingStorage.get(training1.getTrainingKey());
        assertNotNull(foundTraining);
        assertEquals(training1.toString(), foundTraining.toString());
        foundTraining = trainingStorage.get(training2.getTrainingKey());
        assertNotNull(foundTraining);
        assertEquals(training2.toString(), foundTraining.toString());
    }

}
