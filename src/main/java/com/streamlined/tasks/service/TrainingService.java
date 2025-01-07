package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.dto.TrainingDto;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityQueryException;

public interface TrainingService {

    void create(TrainingDto dto) throws EntityCreationException;

    Optional<TrainingDto> findById(TrainingKey key) throws EntityQueryException;

    Stream<TrainingDto> findAll() throws EntityQueryException;

}
