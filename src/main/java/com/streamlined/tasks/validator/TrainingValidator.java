package com.streamlined.tasks.validator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.exception.InvalidEntityDataException;

@Component
public class TrainingValidator extends EntityValidator implements Validator<Training> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingValidator.class);

    public static final LocalDate MIN_START_DATE = LocalDate.of(2000, 1, 1);
    public static final Duration MIN_DURATION = Duration.of(1, ChronoUnit.HOURS);
    public static final Duration MAX_DURATION = Duration.of(90, ChronoUnit.DAYS);

    @Override
    public boolean isValid(Training training) {
        try {
            checkUserId(training.getTraineeId());
            checkUserId(training.getTrainerId());
            checkName(training.getName());
            checkDate(training.getDate());
            checkDuration(training.getDuration());
            return true;
        } catch (InvalidEntityDataException e) {
            LOGGER.info("Invalid training entity: {}", e.getMessage(), e);
            return false;
        }
    }

    private void checkName(String name) {
        if (!containsValidNameCharacters(name))
            throw new InvalidEntityDataException(
                    "Training name should contain only Latin characters and start with capital letter");
    }

    private void checkDate(LocalDate date) {
        if (date.isBefore(MIN_START_DATE))
            throw new InvalidEntityDataException("Training should start after %tF".formatted(MIN_START_DATE));
    }

    private void checkDuration(Duration duration) {
        if (duration.compareTo(MIN_DURATION) < 0)
            throw new InvalidEntityDataException(
                    "Training should last not less than %s".formatted(MIN_DURATION.toString()));
        if (duration.compareTo(MAX_DURATION) > 0)
            throw new InvalidEntityDataException(
                    "Training should last not more than %s".formatted(MAX_DURATION.toString()));
    }

}
