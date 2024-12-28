package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.dto.TraineeDto;

public interface TraineeService {

	void create(TraineeDto dto, char[] password);

	void update(TraineeDto dto);

	void updatePassword(Long id, char[] password);

	void deleteById(Long id);

	Optional<TraineeDto> findById(Long id);

	Stream<TraineeDto> findAll();

}
