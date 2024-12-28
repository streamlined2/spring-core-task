package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.dto.TraineeDto;

public interface TraineeService {

	void create(TraineeDto dto, String password);

	void update(TraineeDto dto);

	void updatePassword(Long id, String password);

	void deleteById(Long id);

	Optional<TraineeDto> findById(Long id);

	Stream<TraineeDto> findAll();

}
