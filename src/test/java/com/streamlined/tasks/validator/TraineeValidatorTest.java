package com.streamlined.tasks.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import com.streamlined.tasks.entity.Trainee;

class TraineeValidatorTest {

    private TraineeValidator traineeValidator = new TraineeValidator();

    @Test
    void isValidTraineeShouldReturnTrue_ifTraineeDataValid() {
        Trainee trainee = new Trainee(105L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertTrue(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeUserIdIsNull() {
        Trainee trainee = new Trainee(null, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeUserIdIsNegative() {
        Trainee trainee = new Trainee(-10L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeUserIdIsZero() {
        Trainee trainee = new Trainee(0L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeFirstnameContainsNonLatinCharacter() {
        Trainee trainee = new Trainee(1L, "Jäck", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeFirstnameContainsSpecialCharacter() {
        Trainee trainee = new Trainee(1L, "J@ck", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeFirstLetterOfFirstnameIsNotCapital() {
        Trainee trainee = new Trainee(1L, "jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeLastnameContainsNonLatinCharacter() {
        Trainee trainee = new Trainee(1L, "Jack", "Fäntasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeLastnameContainsSpecialCharacter() {
        Trainee trainee = new Trainee(1L, "Jack", "F@ntasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeFirstLetterOfLastnameIsNotCapital() {
        Trainee trainee = new Trainee(1L, "Jack", "fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeUsernameDoesNotStartWithFirstnameDotLastname() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Ken.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnTrue_ifTraineeUsernameStartsWithFirstnameDotLastnameAndSerialBlank() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "UK");

        assertTrue(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeUsernameStartsWithFirstnameDotLastnameAndSerialIsNegative() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy-6", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeUsernameStartsWithFirstnameDotLastnameAndSerialIsInvalid() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.FantasyABC", true, LocalDate.of(2000, 1, 1), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnTrue_ifTraineeUsernameStartsWithFirstnameDotLastnameAndSerialIsValid() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, LocalDate.of(2000, 1, 1), "UK");

        assertTrue(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeDateOfBirthIsAfterPresentDate() {
        LocalDate presentDate = LocalDate.now();
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy22", true,
                presentDate.plus(1, ChronoUnit.YEARS), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeAgeLessThanMinAge() {
        LocalDate presentDate = LocalDate.now();
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy22", true,
                presentDate.minus(TraineeValidator.MIN_AGE - 1, ChronoUnit.YEARS), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeAgeGreaterThanMaxAge() {
        LocalDate presentDate = LocalDate.now();
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy22", true,
                presentDate.minus(TraineeValidator.MAX_AGE + 1, ChronoUnit.YEARS), "UK");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeAddressContainsSpecialCharacter() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, LocalDate.of(2000, 1, 1), "US@");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnFalse_ifTraineeAddressContainsNonLatinCharacter() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, LocalDate.of(2000, 1, 1), "USÄ");

        assertFalse(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnTrue_ifTraineeAddressContainsPunctuationMarksOrLowercaseCharacters() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, LocalDate.of(2000, 1, 1), "USA (United States of America)");

        assertTrue(traineeValidator.isValid(trainee));
    }

    @Test
    void isValidTraineeShouldReturnTrue_ifTraineeAddressContainsDigits() {
        Trainee trainee = new Trainee(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, LocalDate.of(2000, 1, 1), "USA123");

        assertTrue(traineeValidator.isValid(trainee));
    }

}
