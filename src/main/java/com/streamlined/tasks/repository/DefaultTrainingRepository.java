package com.streamlined.tasks.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.storage.TrainingStorage;

@Repository
public class DefaultTrainingRepository implements TrainingRepository {

    private static final Logger log = LoggerFactory.getLogger(DefaultTrainingRepository.class);

    private final TrainingStorage trainingStorage;

    public DefaultTrainingRepository(TrainingStorage trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public void create(Training training) {
        TrainingKey trainingKey = training.getTrainingKey();
        if (trainingStorage.get(trainingKey) != null) {
            log.warn("Training with id {} already exists", trainingKey);
            throw new EntityAlreadyExistsException(
                    "Training with id %s already exists".formatted(trainingKey.toString()));
        }
        trainingStorage.saveNew(training);
    }

    @Override
    public Optional<Training> findById(TrainingKey key) {
        return Optional.ofNullable(trainingStorage.get(key));
    }

    @Override
    public Stream<Training> findAll() {
        return trainingStorage.getAll();
    }

}
