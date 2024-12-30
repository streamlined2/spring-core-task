package com.streamlined.tasks.repository;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;

public interface TrainingRepository {

    void create(Training entity);

    Optional<Training> findById(TrainingKey key);

    Stream<Training> findAll();

}
