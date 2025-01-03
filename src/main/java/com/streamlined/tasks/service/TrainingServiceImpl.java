package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.streamlined.tasks.dto.TrainingDto;
import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.mapper.TrainingMapper;
import com.streamlined.tasks.parser.Parser;
import com.streamlined.tasks.repository.TrainingRepository;

import jakarta.annotation.PostConstruct;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private final TrainingMapper trainingMapper;
    private final TrainingRepository trainingRepository;
    private final Parser parser;
    private final String sourceFileName;

    public TrainingServiceImpl(TrainingMapper trainingMapper, TrainingRepository trainingRepository, Parser parser,
            @Value("${source.csv.training}") String sourceFileName) {
        this.trainingMapper = trainingMapper;
        this.trainingRepository = trainingRepository;
        this.parser = parser;
        this.sourceFileName = sourceFileName;
    }

    @PostConstruct
    private void initilialize() {
        trainingRepository.addAll(parser.parse(Training.class, sourceFileName));
    }

    @Override
    public void create(TrainingDto dto) {
        try {
            Training training = trainingMapper.toEntity(dto);
            trainingRepository.create(training);
        } catch (Exception e) {
            LOGGER.debug("Error creating training entity", e);
            throw new EntityCreationException("Error creating training entity", e);
        }
    }

    @Override
    public Optional<TrainingDto> findById(TrainingKey key) {
        try {
            return trainingRepository.findById(key).map(trainingMapper::toDto);
        } catch (Exception e) {
            LOGGER.debug("Error querying training entity", e);
            throw new EntityQueryException("Error querying training entity", e);
        }
    }

    @Override
    public Stream<TrainingDto> findAll() {
        try {
            return trainingRepository.findAll().map(trainingMapper::toDto);
        } catch (Exception e) {
            LOGGER.debug("Error querying training entity", e);
            throw new EntityQueryException("Error querying training entity", e);
        }
    }

}
