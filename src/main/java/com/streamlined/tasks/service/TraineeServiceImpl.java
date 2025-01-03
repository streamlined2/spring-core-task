package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityDeletionException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.exception.EntityUpdateException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.mapper.TraineeMapper;
import com.streamlined.tasks.parser.Parser;

import jakarta.annotation.PostConstruct;

@Service
public class TraineeServiceImpl extends UserServiceImpl implements TraineeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final TraineeMapper traineeMapper;
    private final SecurityService securityService;
    private final Parser parser;
    private final String sourceFileName;

    public TraineeServiceImpl(TraineeMapper traineeMapper, SecurityService securityService, Parser parser,
            @Value("${source.csv.trainee}") String sourceFileName) {
        this.traineeMapper = traineeMapper;
        this.securityService = securityService;
        this.parser = parser;
        this.sourceFileName = sourceFileName;
    }

    @PostConstruct
    private void initilialize() {
        traineeRepository.addAll(parser.parse(Trainee.class, sourceFileName));
    }

    @Override
    public void create(TraineeDto dto, char[] password) {
        try {
            Trainee trainee = traineeMapper.toEntity(dto);
            trainee.setPasswordHash(securityService.getPasswordHash(password));
            setNextUsernameSerial(trainee);
            traineeRepository.create(trainee);
        } catch (Exception e) {
            LOGGER.debug("Error creating trainee entity", e);
            throw new EntityCreationException("Error creating trainee entity", e);
        }
    }

    @Override
    public void update(TraineeDto dto) {
        try {
            Trainee trainee = traineeRepository.findById(dto.userId()).orElseThrow(
                    () -> new NoSuchEntityException("No trainee entity with id %d".formatted(dto.userId())));
            Trainee newTrainee = traineeMapper.toEntity(dto);
            newTrainee.setPasswordHash(trainee.getPasswordHash());
            newTrainee.setUserName(trainee.getUserName());
            traineeRepository.update(newTrainee);
        } catch (Exception e) {
            LOGGER.debug("Error updating trainee entity", e);
            throw new EntityUpdateException("Error updating trainee entity", e);
        }
    }

    @Override
    public void updatePassword(Long id, char[] password) {
        try {
            Trainee trainee = traineeRepository.findById(id)
                    .orElseThrow(() -> new NoSuchEntityException("No trainee entity with id %d".formatted(id)));
            trainee.setPasswordHash(securityService.getPasswordHash(password));
            traineeRepository.update(trainee);
        } catch (Exception e) {
            LOGGER.debug("Error updating password for trainee entity", e);
            throw new EntityUpdateException("Error updating password for trainee entity", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            traineeRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.debug("Error deleting trainee entity", e);
            throw new EntityDeletionException("Error deleting trainee entity", e);
        }
    }

    @Override
    public Optional<TraineeDto> findById(Long id) {
        try {
            return traineeRepository.findById(id).map(traineeMapper::toDto);
        } catch (Exception e) {
            LOGGER.debug("Error querying trainee entity", e);
            throw new EntityQueryException("Error querying trainee entity", e);
        }
    }

    @Override
    public Stream<TraineeDto> findAll() {
        try {
            return traineeRepository.findAll().map(traineeMapper::toDto);
        } catch (Exception e) {
            LOGGER.debug("Error querying trainee entity", e);
            throw new EntityQueryException("Error querying trainee entity", e);
        }
    }

}
