package com.streamlined.tasks.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.streamlined.tasks.entity.Trainer;

class TrainerValidatorTest {

    private TrainerValidator trainerValidator = new TrainerValidator();

    @Test
    void isValidTrainerShouldReturnTrue_ifTrainerDataValid() {
        Trainer trainer = new Trainer(105L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");

        assertTrue(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerUserIdIsNull() {
        Trainer trainer = new Trainer(null, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerUserIdIsNegative() {
        Trainer trainer = new Trainer(-10L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerUserIdIsZero() {
        Trainer trainer = new Trainer(0L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerFirstnameContainsNonLatinCharacter() {
        Trainer trainer = new Trainer(1L, "Jäck", "Fantasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerFirstnameContainsSpecialCharacter() {
        Trainer trainer = new Trainer(1L, "J@ck", "Fantasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerFirstLetterOfFirstnameIsNotCapital() {
        Trainer trainer = new Trainer(1L, "jack", "Fantasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerLastnameContainsNonLatinCharacter() {
        Trainer trainer = new Trainer(1L, "Jack", "Fäntasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerLastnameContainsSpecialCharacter() {
        Trainer trainer = new Trainer(1L, "Jack", "F@ntasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerFirstLetterOfLastnameIsNotCapital() {
        Trainer trainer = new Trainer(1L, "Jack", "fantasy", "Jack.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerUsernameDoesNotStartWithFirstnameDotLastname() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Ken.Fantasy", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnTrue_ifTrainerUsernameStartsWithFirstnameDotLastnameAndSerialBlank() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");

        assertTrue(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerUsernameStartsWithFirstnameDotLastnameAndSerialIsNegative() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Jack.Fantasy-6", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerUsernameStartsWithFirstnameDotLastnameAndSerialIsInvalid() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Jack.FantasyABC", true, "Math");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnTrue_ifTrainerUsernameStartsWithFirstnameDotLastnameAndSerialIsValid() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, "Math");

        assertTrue(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerSpecializationContainsSpecialCharacter() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, "M@th");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnFalse_ifTrainerSpecializationContainsNonLatinCharacter() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, "Mäth");

        assertFalse(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnTrue_ifTrainerSpecializationContainsPunctuationMarksOrLowercaseCharacters() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, "Math!");

        assertTrue(trainerValidator.isValid(trainer));
    }

    @Test
    void isValidTrainerShouldReturnTrue_ifTrainerSpecializationContainsDigits() {
        Trainer trainer = new Trainer(1L, "Jack", "Fantasy", "Jack.Fantasy22", true, "Math123");

        assertTrue(trainerValidator.isValid(trainer));
    }

}
