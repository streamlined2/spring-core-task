package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.dto.TrainerDto;

public interface TrainerService {

	void create(TrainerDto dto, char[] password);

	void update(TrainerDto dto);

	void updatePassword(Long id, char[] password);

	void deleteById(Long id);

	Optional<TrainerDto> findById(Long id);

	Stream<TrainerDto> findAll();

}
