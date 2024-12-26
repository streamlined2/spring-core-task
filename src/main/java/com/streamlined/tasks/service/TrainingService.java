package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.dto.TrainingDto;
import com.streamlined.tasks.entity.Training.TrainingKey;

public interface TrainingService {

	void create(TrainingDto dto);

	Optional<TrainingDto> findById(TrainingKey key);

	Stream<TrainingDto> findAll();

}
