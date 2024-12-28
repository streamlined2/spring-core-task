package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.exception.EntityCreationException;
import com.streamlined.tasks.exception.EntityDeletionException;
import com.streamlined.tasks.exception.EntityQueryException;
import com.streamlined.tasks.exception.EntityUpdateException;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.mapper.TraineeMapper;
import com.streamlined.tasks.repository.TraineeRepository;

@Service
public class DefaultTraineeService implements TraineeService {

	private static final Logger log = LoggerFactory.getLogger(DefaultTraineeService.class);

	private final TraineeMapper traineeMapper;
	private final TraineeRepository traineeRepository;
	private final SecurityService securityService;

	public DefaultTraineeService(TraineeMapper traineeMapper, TraineeRepository traineeRepository,
			SecurityService securityService) {
		this.traineeMapper = traineeMapper;
		this.traineeRepository = traineeRepository;
		this.securityService = securityService;
	}

	@Override
	public void create(TraineeDto dto, char[] password) {
		try {
			Trainee trainee = traineeMapper.toEntity(dto);
			trainee.setPasswordHash(securityService.getPasswordHash(password));
			trainee.setNextUsernameSerial(traineeRepository.getMaxUsernameSerial(dto.firstName(), dto.lastName()));
			traineeRepository.create(trainee);
		} catch (Exception e) {
			log.error("Error creating trainee entity", e);
			throw new EntityCreationException("Error creating trainee entity", e);
		}
	}

	@Override
	public void update(TraineeDto dto) {
		try {
			Trainee trainee = traineeRepository.findById(dto.userId()).orElseThrow(
					() -> new NoSuchEntityException("No trainee entity with id %d".formatted(dto.userId())));
			Trainee newTrainee = traineeMapper.toEntity(dto);
			newTrainee.setPasswordHash(trainee.getPasswordHash());
			newTrainee.setUserName(trainee.getUserName());
			traineeRepository.update(newTrainee);
		} catch (Exception e) {
			log.error("Error updating trainee entity", e);
			throw new EntityUpdateException("Error updating trainee entity", e);
		}
	}

	@Override
	public void updatePassword(Long id, char[] password) {
		try {
			Trainee trainee = traineeRepository.findById(id)
					.orElseThrow(() -> new NoSuchEntityException("No trainee entity with id %d".formatted(id)));
			trainee.setPasswordHash(securityService.getPasswordHash(password));
			traineeRepository.update(trainee);
		} catch (Exception e) {
			log.error("Error updating password for trainee entity", e);
			throw new EntityUpdateException("Error updating password for trainee entity", e);
		}
	}

	@Override
	public void deleteById(Long id) {
		try {
			traineeRepository.deleteById(id);
		} catch (Exception e) {
			log.error("Error deleting trainee entity", e);
			throw new EntityDeletionException("Error deleting trainee entity", e);
		}
	}

	@Override
	public Optional<TraineeDto> findById(Long id) {
		try {
			return traineeRepository.findById(id).map(traineeMapper::toDto);
		} catch (Exception e) {
			log.error("Error querying trainee entity", e);
			throw new EntityQueryException("Error querying trainee entity", e);
		}
	}

	@Override
	public Stream<TraineeDto> findAll() {
		try {
			return traineeRepository.findAll().map(traineeMapper::toDto);
		} catch (Exception e) {
			log.error("Error querying trainee entity", e);
			throw new EntityQueryException("Error querying trainee entity", e);
		}
	}

}
