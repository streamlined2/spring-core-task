package com.streamlined.tasks.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.storage.TrainingStorage;

@Repository
public class DefaultTrainingRepository implements TrainingRepository {

    private final TrainingStorage trainingStorage;

    public DefaultTrainingRepository(TrainingStorage trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public void create(Training training) {
        TrainingKey trainingKey = training.getPrimaryKey();
        if (trainingStorage.get(trainingKey) != null) {
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
