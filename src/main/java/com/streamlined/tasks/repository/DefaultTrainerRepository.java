package com.streamlined.tasks.repository;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.EntityAlreadyExistsException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.storage.TrainerStorage;

@Repository
public class DefaultTrainerRepository implements TrainerRepository {

	private static final Logger log = LoggerFactory.getLogger(DefaultTrainerRepository.class);

	private final TrainerStorage trainerStorage;

	public DefaultTrainerRepository(TrainerStorage trainerStorage) {
		this.trainerStorage = trainerStorage;
	}

	@Override
	public void create(Trainer trainer) {
		Trainer oldTrainer = trainerStorage.saveNew(trainer);
		if (oldTrainer != null) {
			log.warn("Trainer with id {} already exists", trainer.getUserId());
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
			log.warn("No entity found with id {}", id);
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

	@Override
	public Optional<String> getMaxUsernameSerial(String firstName, String lastName) {
		return findAll().filter(trainer -> trainer.userNameStartsWith(firstName, lastName))
				.map(trainer -> trainer.getUsernameSerial(firstName, lastName)).max(Comparator.naturalOrder());
	}

}
