package com.streamlined.tasks.repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.exception.NoSuchEntityException;

public interface TraineeRepository {

    void create(Trainee entity) throws EntityAlreadyExistsException;

    void update(Trainee entity);

    void deleteById(Long id) throws NoSuchEntityException;

    Optional<Trainee> findById(Long id);

    Stream<Trainee> findAll();

    Optional<String> getMaxUsernameSerial(String firstName, String lastName);

    void addAll(Map<Long, Trainee> map);

}
