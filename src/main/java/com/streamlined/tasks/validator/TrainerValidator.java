package com.streamlined.tasks.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.InvalidEntityDataException;

@Component
public class TrainerValidator extends UserValidator implements Validator<Trainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerValidator.class);

    @Override
    public boolean isValid(Trainer trainer) {
        if (!super.isValid(trainer))
            return false;
        try {
            checkSpecialization(trainer.getSpecialization());
            return true;
        } catch (InvalidEntityDataException e) {
            LOGGER.info("Invalid trainer entity: {}", e.getMessage(), e);
            return false;
        }
    }

    private void checkSpecialization(String address) {
        if (!containsValidSpecializationCharacters(address))
            throw new InvalidEntityDataException(
                    "Specialization should contain only Latin characters, punctuation marks, or digits");
    }

    private boolean containsValidSpecializationCharacters(String value) {
        for (int k = 0; k < value.length(); k++) {
            char ch = value.charAt(k);
            if (!punctuationCharSet.contains(ch) && !digitCharSet.contains(ch) && !validCapitalCharSet.contains(ch)
                    && !validLowerCaseCharSet.contains(ch)) {
                return false;
            }
        }
        return true;
    }

}
