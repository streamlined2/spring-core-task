package com.streamlined.tasks.repository;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.storage.HashMapStorage;

@Repository
public class HashMapTraineeRepository implements TraineeRepository {

    private final HashMapStorage<Long, Trainee> traineeStorage;

    public HashMapTraineeRepository(@Qualifier("traineeStorage") HashMapStorage<Long, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public void create(Trainee trainee) {
        Trainee oldTrainee = traineeStorage.saveNew(trainee);
        if (oldTrainee != null) {
            throw new EntityAlreadyExistsException("Trainee with id %d already exists".formatted(trainee.getUserId()));
        }
    }

    @Override
    public void update(Trainee trainee) {
        traineeStorage.save(trainee.getUserId(), trainee);
    }

    @Override
    public void deleteById(Long id) {
        Trainee trainee = traineeStorage.get(id);
        if (trainee == null) {
            throw new NoSuchEntityException("No entity found with id %d".formatted(id));
        }
        traineeStorage.remove(id);
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(traineeStorage.get(id));
    }

    @Override
    public Stream<Trainee> findAll() {
        return traineeStorage.getAll();
    }

    @Override
    public Optional<String> getMaxUsernameSerial(String firstName, String lastName) {
        return findAll().filter(trainee -> trainee.userNameStartsWith(firstName, lastName))
                .map(Trainee::getUsernameSerial).max(Comparator.naturalOrder());
    }

    @Override
    public void addAll(Map<Long, Trainee> map) {
        traineeStorage.addAll(map);
    }

}
