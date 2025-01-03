package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityDeletionException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.exception.EntityUpdateException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.mapper.TrainerMapper;
import com.streamlined.tasks.parser.Parser;

import jakarta.annotation.PostConstruct;

@Service
public class TrainerServiceImpl extends UserServiceImpl implements TrainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final TrainerMapper trainerMapper;
    private final SecurityService securityService;
    private final Parser parser;
    private final String sourceFileName;

    public TrainerServiceImpl(TrainerMapper trainerMapper, SecurityService securityService, Parser parser,
            @Value("${source.csv.trainer}") String sourceFileName) {
        this.trainerMapper = trainerMapper;
        this.securityService = securityService;
        this.parser = parser;
        this.sourceFileName = sourceFileName;
    }

    @PostConstruct
    private void initilialize() {
        trainerRepository.addAll(parser.parse(Trainer.class, sourceFileName));
    }

    @Override
    public void create(TrainerDto dto, char[] password) {
        try {
            Trainer trainer = trainerMapper.toEntity(dto);
            trainer.setPasswordHash(securityService.getPasswordHash(password));
            setNextUsernameSerial(trainer);
            trainerRepository.create(trainer);
        } catch (Exception e) {
            LOGGER.debug("Error creating trainer entity", e);
            throw new EntityCreationException("Error creating trainer entity", e);
        }
    }

    @Override
    public void update(TrainerDto dto) {
        try {
            Trainer trainer = trainerRepository.findById(dto.userId()).orElseThrow(
                    () -> new NoSuchEntityException("No trainer entity with id %d".formatted(dto.userId())));
            Trainer newTrainer = trainerMapper.toEntity(dto);
            newTrainer.setPasswordHash(trainer.getPasswordHash());
            newTrainer.setUserName(trainer.getUserName());
            trainerRepository.update(newTrainer);
        } catch (Exception e) {
            LOGGER.debug("Error updating trainer entity", e);
            throw new EntityUpdateException("Error updating trainer entity", e);
        }
    }

    @Override
    public void updatePassword(Long id, char[] password) {
        try {
            Trainer trainer = trainerRepository.findById(id)
                    .orElseThrow(() -> new NoSuchEntityException("No trainer entity with id %d".formatted(id)));
            trainer.setPasswordHash(securityService.getPasswordHash(password));
            trainerRepository.update(trainer);
        } catch (Exception e) {
            LOGGER.debug("Error updating password for trainer entity", e);
            throw new EntityUpdateException("Error updating password for trainer entity", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            trainerRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.debug("Error deleting trainer entity", e);
            throw new EntityDeletionException("Error deleting trainer entity", e);
        }
    }

    @Override
    public Optional<TrainerDto> findById(Long id) {
        try {
            return trainerRepository.findById(id).map(trainerMapper::toDto);
        } catch (Exception e) {
            LOGGER.debug("Error querying trainer entity", e);
            throw new EntityQueryException("Error querying trainer entity", e);
        }
    }

    @Override
    public Stream<TrainerDto> findAll() {
        try {
            return trainerRepository.findAll().map(trainerMapper::toDto);
        } catch (Exception e) {
            LOGGER.debug("Error querying trainer entity", e);
            throw new EntityQueryException("Error querying trainer entity", e);
        }
    }

}
