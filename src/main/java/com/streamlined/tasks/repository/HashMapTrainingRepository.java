package com.streamlined.tasks.repository;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.storage.HashMapStorage;

@Repository
public class HashMapTrainingRepository implements TrainingRepository {

    private final HashMapStorage<Training.TrainingKey, Training> trainingStorage;

    public HashMapTrainingRepository(
            @Qualifier("trainingStorage") HashMapStorage<Training.TrainingKey, Training> trainingStorage) {
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

    @Override
    public void addAll(Map<Training.TrainingKey, Training> map) {
        trainingStorage.addAll(map);
    }

}
