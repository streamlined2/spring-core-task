package com.streamlined.tasks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
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

import com.streamlined.tasks.dto.TrainingDto;
import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.entity.TrainingType;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.mapper.TrainingMapper;
import com.streamlined.tasks.repository.TrainingRepository;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Spy
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingServiceImpl trainingService;

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
    @DisplayName("create adds new entity")
    void testCreate_shouldCreateNewEntity_ifCreateSucceeds() {
        Training training = new Training(5L, 2L, "Art training", artType, LocalDate.of(2022, 1, 1),
                Duration.ofDays(60));
        TrainingDto trainingDto = trainingMapper.toDto(training);
        TrainingKey trainingKey = training.getPrimaryKey();
        List<Training> trainingList = new ArrayList<>(
                List.of(new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1), Duration.ofDays(20)),
                        new Training(2L, 1L, "Math training", mathType, LocalDate.of(2021, 1, 1), Duration.ofDays(20)),
                        new Training(3L, 1L, "Math training", mathType, LocalDate.of(2022, 1, 1), Duration.ofDays(20)),
                        new Training(3L, 2L, "Art training", artType, LocalDate.of(2020, 1, 1), Duration.ofDays(60)),
                        new Training(4L, 2L, "Art training", artType, LocalDate.of(2021, 1, 1), Duration.ofDays(60)),
                        new Training(1L, 3L, "Computer science training", computerType, LocalDate.of(2020, 1, 1),
                                Duration.ofDays(120)),
                        new Training(3L, 3L, "Computer science training", computerType, LocalDate.of(2021, 1, 1),
                                Duration.ofDays(120)),
                        new Training(5L, 3L, "Computer science training", computerType, LocalDate.of(2022, 1, 1),
                                Duration.ofDays(120))));
        final int initialTrainingListSize = trainingList.size();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                trainingList.add(training);
                return null;
            }
        }).when(trainingRepository).create(training);

        trainingService.create(trainingDto);

        assertEquals(initialTrainingListSize + 1, trainingList.size());
        assertTrue(trainingList.contains(training));
        Optional<Training> newTraining = trainingList.stream().filter(t -> t.getPrimaryKey().equals(trainingKey))
                .findFirst();
        assertTrue(newTraining.isPresent());
        assertEquals(trainingDto, trainingMapper.toDto(newTraining.get()));
        verify(trainingRepository).create(training);
    }

    @Test
    @DisplayName("create throws exception if training repository throws exception")
    void testCreate_shouldThrowEntityCreationException_ifTrainingRepositoryThrowsException() {
        Training training = new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));
        TrainingDto trainingDto = trainingMapper.toDto(training);
        final String errorMessage = "exception prevented new training entity creation";

        doAnswer(new ThrowsException(new RuntimeException(errorMessage))).when(trainingRepository).create(training);

        Exception e = assertThrows(EntityCreationException.class, () -> trainingService.create(trainingDto));
        assertEquals("Error creating training entity", e.getMessage());
        assertEquals(errorMessage, e.getCause().getMessage());
        verify(trainingRepository).create(training);
    }

    @Test
    @DisplayName("findById fetches training entity for given training key")
    void testFindById_shouldReturnFoundTrainingEntity_ifPassedIdValidAndExists() {
        Training expectedTraining = new Training(3L, 1L, "Math training", mathType, LocalDate.of(2022, 1, 1),
                Duration.ofDays(20));
        TrainingDto expectedTrainingDto = trainingMapper.toDto(expectedTraining);
        when(trainingRepository.findById(expectedTraining.getPrimaryKey())).thenReturn(Optional.of(expectedTraining));

        Optional<TrainingDto> actualTrainingDto = trainingService.findById(expectedTraining.getPrimaryKey());

        verify(trainingRepository).findById(expectedTraining.getPrimaryKey());
        assertTrue(actualTrainingDto.isPresent());
        assertEquals(expectedTrainingDto, actualTrainingDto.get());
    }

    @Test
    @DisplayName("findById returns empty Optional if given training key does not exist")
    void testFindById_shouldReturnEmptyOptional_ifPassedIdDoesNotExist() {
        TrainingKey trainingKey = new TrainingKey(1L, 1L, LocalDate.of(1900, 1, 1));
        when(trainingRepository.findById(trainingKey)).thenReturn(Optional.empty());

        Optional<TrainingDto> actualTrainingDto = trainingService.findById(trainingKey);

        verify(trainingRepository).findById(trainingKey);
        assertTrue(actualTrainingDto.isEmpty());
    }

    @Test
    @DisplayName("findById throws exception if training repository throws exception")
    void testFindById_shouldThrowEntityQueryException_ifTrainingRepositoryThrowsException() {
        TrainingKey trainingKey = new TrainingKey(1L, 1L, LocalDate.of(1900, 1, 1));
        when(trainingRepository.findById(trainingKey)).thenThrow(EntityQueryException.class);

        assertThrows(EntityQueryException.class, () -> trainingService.findById(trainingKey));
        verify(trainingRepository).findById(trainingKey);
    }

    @Test
    @DisplayName("findAll fetches all available training entities")
    void testFindAll_shouldReturnAllAvailableTrainingEntities_ifTrainingRepositoryReturnsResult() {
        List<Training> expectedTrainingList = List.of(
                new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1), Duration.ofDays(20)),
                new Training(2L, 1L, "Math training", mathType, LocalDate.of(2021, 1, 1), Duration.ofDays(20)),
                new Training(3L, 1L, "Math training", mathType, LocalDate.of(2022, 1, 1), Duration.ofDays(20)),
                new Training(3L, 2L, "Art training", artType, LocalDate.of(2020, 1, 1), Duration.ofDays(60)),
                new Training(4L, 2L, "Art training", artType, LocalDate.of(2021, 1, 1), Duration.ofDays(60)),
                new Training(5L, 2L, "Art training", artType, LocalDate.of(2022, 1, 1), Duration.ofDays(60)),
                new Training(1L, 3L, "Computer science training", computerType, LocalDate.of(2020, 1, 1),
                        Duration.ofDays(120)),
                new Training(3L, 3L, "Computer science training", computerType, LocalDate.of(2021, 1, 1),
                        Duration.ofDays(120)),
                new Training(5L, 3L, "Computer science training", computerType, LocalDate.of(2022, 1, 1),
                        Duration.ofDays(120)));

        when(trainingRepository.findAll()).thenReturn(expectedTrainingList.stream());

        List<TrainingDto> resultingDtoList = trainingService.findAll().toList();
        List<TrainingDto> expectedDtoList = expectedTrainingList.stream().map(trainingMapper::toDto).toList();

        assertEquals(expectedDtoList, resultingDtoList);
        verify(trainingRepository).findAll();
    }

    @Test
    @DisplayName("findAll throws exception if training repository throws exception")
    void testFindAll_shouldThrowEntityQueryException_ifTrainingRepositoryThrowsException() {
        when(trainingRepository.findAll()).thenThrow(RuntimeException.class);

        assertThrows(EntityQueryException.class, () -> trainingService.findAll());
        verify(trainingRepository).findAll();
    }

}
