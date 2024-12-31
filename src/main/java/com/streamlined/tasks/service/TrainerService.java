package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityDeletionException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.exception.EntityUpdateException;

public interface TrainerService {

    void create(TrainerDto dto, char[] password) throws EntityCreationException;

    void update(TrainerDto dto) throws EntityUpdateException;

    void updatePassword(Long id, char[] password) throws EntityUpdateException;

    void deleteById(Long id) throws EntityDeletionException;

    Optional<TrainerDto> findById(Long id) throws EntityQueryException;

    Stream<TrainerDto> findAll() throws EntityQueryException;

}
