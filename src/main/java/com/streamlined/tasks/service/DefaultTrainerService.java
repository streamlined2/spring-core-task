package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityDeletionException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.exception.EntityUpdateException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.mapper.TrainerMapper;
import com.streamlined.tasks.repository.TrainerRepository;

@Service
public class DefaultTrainerService implements TrainerService {

	private static final Logger log = LoggerFactory.getLogger(DefaultTrainerService.class);

	private final TrainerMapper trainerMapper;
	private final TrainerRepository trainerRepository;
	private final PasswordEncoder passwordEncoder;

	public DefaultTrainerService(TrainerMapper trainerMapper, TrainerRepository trainerRepository,
			PasswordEncoder passwordEncoder) {
		this.trainerMapper = trainerMapper;
		this.trainerRepository = trainerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void create(TrainerDto dto, String password) {
		try {
			Trainer trainer = trainerMapper.toEntity(dto);
			trainer.setPasswordHash(passwordEncoder.encode(password));
			trainer.setNextUsernameSerial(trainerRepository.getMaxUsernameSerial(dto.firstName(), dto.lastName()));
			trainerRepository.create(trainer);
		} catch (Exception e) {
			log.error("Error creating trainer entity", e);
			throw new EntityCreationException("Error creating trainer entity", e);
		}
	}

	@Override
	public void update(TrainerDto dto) {
		try {
			Trainer trainer = trainerRepository.findById(dto.userId()).orElseThrow(
					() -> new NoSuchEntityException("No trainer entity with id %d".formatted(dto.userId())));
			Trainer newTrainer = trainerMapper.toEntity(dto);
			newTrainer.setPasswordHash(trainer.getPasswordHash());
			newTrainer.setUserName(trainer.getUserName());
			trainerRepository.update(newTrainer);
		} catch (Exception e) {
			log.error("Error updating trainer entity", e);
			throw new EntityUpdateException("Error updating trainer entity", e);
		}
	}

	@Override
	public void updatePassword(Long id, String password) {
		try {
			Trainer trainer = trainerRepository.findById(id)
					.orElseThrow(() -> new NoSuchEntityException("No trainer entity with id %d".formatted(id)));
			trainer.setPasswordHash(passwordEncoder.encode(password));
			trainerRepository.update(trainer);
		} catch (Exception e) {
			log.error("Error updating password for trainer entity", e);
			throw new EntityUpdateException("Error updating password for trainer entity", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			trainerRepository.deleteById(id);
		} catch (Exception e) {
			log.error("Error deleting trainer entity", e);
			throw new EntityDeletionException("Error deleting trainer entity", e);
		}
	}

	@Override
	public Optional<TrainerDto> findById(Long id) {
		try {
			return trainerRepository.findById(id).map(trainerMapper::toDto);
		} catch (Exception e) {
			log.error("Error querying trainer entity", e);
			throw new EntityQueryException("Error querying trainer entity", e);
		}
	}

	@Override
	public Stream<TrainerDto> findAll() {
		try {
			return trainerRepository.findAll().map(trainerMapper::toDto);
		} catch (Exception e) {
			log.error("Error querying trainer entity", e);
			throw new EntityQueryException("Error querying trainer entity", e);
		}
	}

}
