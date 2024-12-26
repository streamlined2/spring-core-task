package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.streamlined.tasks.dto.TrainingDto;
import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.mapper.TrainingMapper;
import com.streamlined.tasks.repository.TrainingRepository;

@Service
public class DefaultTrainingService implements TrainingService {

	private final TrainingMapper trainingMapper;
	private final TrainingRepository trainingRepository;

	public DefaultTrainingService(TrainingMapper trainingMapper, TrainingRepository trainingRepository) {
		this.trainingMapper = trainingMapper;
		this.trainingRepository = trainingRepository;
	}

	@Override
	public void create(TrainingDto dto) {
		Training training = trainingMapper.toEntity(dto);
		trainingRepository.create(training);
	}

	@Override
	public Optional<TrainingDto> findById(TrainingKey key) {
		return trainingRepository.findById(key).map(trainingMapper::toDto);
	}

	@Override
	public Stream<TrainingDto> findAll() {
		return trainingRepository.findAll().map(trainingMapper::toDto);
	}

}
