package com.streamlined.tasks.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.TrainingType;

class TrainingValidatorTest {

    private TrainingValidator trainingValidator = new TrainingValidator();

    @Test
    void isValidTrainingShouldReturnTrue_ifTrainingDataValid() {
        Training training = new Training(1L, 1L, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertTrue(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTraineeIdIsNull() {
        Training training = new Training(null, 1L, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainerIdIsNull() {
        Training training = new Training(1L, null, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTraineeIdIsNegative() {
        Training training = new Training(-1L, 1L, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainerIdIsNegative() {
        Training training = new Training(1L, -1L, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTraineeIdIsZero() {
        Training training = new Training(0L, 1L, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainerIdIsZero() {
        Training training = new Training(1L, 0L, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainingNameContainsNonLatinCharacter() {
        Training training = new Training(1L, 1L, "Mäth training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainingNameContainsContainsSpecialCharacter() {
        Training training = new Training(1L, 1L, "M@th training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainingNameFirstLetterOfNameIsNotCapital() {
        Training training = new Training(1L, 1L, "math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainingDateIsBeforeMinStartDate() {
        LocalDate startDate = TrainingValidator.MIN_START_DATE.minus(1, ChronoUnit.DAYS);
        Training training = new Training(1L, 1L, "Math training", new TrainingType("Math"), startDate,
                Duration.ofDays(20));

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainingDurationIsLessThanMinDuration() {
        Duration minDuration = TrainingValidator.MIN_DURATION.minus(1, ChronoUnit.SECONDS);
        Training training = new Training(1L, 1L, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                minDuration);

        assertFalse(trainingValidator.isValid(training));
    }

    @Test
    void isValidTrainingShouldReturnFalse_ifTrainingDurationIsGreaterThanMaxDuration() {
        Duration maxDuration = TrainingValidator.MAX_DURATION.plus(1, ChronoUnit.SECONDS);
        Training training = new Training(1L, 1L, "Math training", new TrainingType("Math"), LocalDate.of(2020, 1, 1),
                maxDuration);

        assertFalse(trainingValidator.isValid(training));
    }

}
