package com.streamlined.tasks.repository;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.exception.NoSuchEntityException;

public interface TrainerRepository {

    void create(Trainer entity) throws EntityAlreadyExistsException;

    void update(Trainer entity);

    void deleteById(Long id) throws NoSuchEntityException;

    Optional<Trainer> findById(Long id);

    Stream<Trainer> findAll();

    Optional<String> getMaxUsernameSerial(String firstName, String lastName);

}
