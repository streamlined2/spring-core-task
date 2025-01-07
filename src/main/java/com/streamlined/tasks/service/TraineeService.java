package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityDeletionException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.exception.EntityUpdateException;

public interface TraineeService {

    void create(TraineeDto dto, char[] password) throws EntityCreationException;

    void update(TraineeDto dto) throws EntityUpdateException;

    void updatePassword(Long id, char[] password) throws EntityUpdateException;

    void deleteById(Long id) throws EntityDeletionException;

    Optional<TraineeDto> findById(Long id) throws EntityQueryException;

    Stream<TraineeDto> findAll() throws EntityQueryException;

}
