package com.streamlined.tasks.repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;

public interface TrainingRepository {

    void create(Training entity) throws EntityAlreadyExistsException;

    Optional<Training> findById(TrainingKey key);

    Stream<Training> findAll();

    void addAll(Map<Training.TrainingKey, Training> map);

}
