package com.streamlined.tasks.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.storage.TrainerStorage;

@Repository
public class DefaultTrainerRepository implements TrainerRepository {

	private final TrainerStorage trainerStorage;

	public DefaultTrainerRepository(TrainerStorage trainerStorage) {
		this.trainerStorage = trainerStorage;
	}

	@Override
	public void create(Trainer trainer) {
		Trainer oldTrainer = trainerStorage.saveNew(trainer);
		if (oldTrainer != null) {
			throw new EntityAlreadyExistsException("Trainer with id %d already exists".formatted(trainer.getUserId()));
		}
	}

	@Override
	public void update(Trainer trainer) {
		trainerStorage.save(trainer.getUserId(), trainer);
	}

	@Override
	public void deleteById(Long id) {
		Trainer trainer = trainerStorage.get(id);
		if (trainer == null) {
			throw new NoSuchEntityException("No entity found with id %d".formatted(id));
		}
		trainerStorage.remove(id);
	}

	@Override
	public Optional<Trainer> findById(Long id) {
		return Optional.ofNullable(trainerStorage.get(id));
	}

	@Override
	public Stream<Trainer> findAll() {
		return trainerStorage.getAll();
	}

}