package com.streamlined.tasks.repository;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.entity.Trainer;

public interface TrainerRepository {

	void create(Trainer entity);

	void update(Trainer entity);

	void deleteById(Long id);

	Optional<Trainer> findById(Long id);

	Stream<Trainer> findAll();

}
