package com.streamlined.tasks.repository;

import java.util.Optional;
import java.util.stream.Stream;

import com.streamlined.tasks.entity.Trainee;

public interface TraineeRepository {

	void create(Trainee entity);

	void update(Trainee entity);

	void deleteById(Long id);

	Optional<Trainee> findById(Long id);

	Stream<Trainee> findAll();

}
