package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.mapper.TraineeMapper;
import com.streamlined.tasks.repository.TraineeRepository;

@Service
public class DefaultTraineeService implements TraineeService {

	private final TraineeMapper traineeMapper;
	private final TraineeRepository traineeRepository;
	private final PasswordEncoder passwordEncoder;

	public DefaultTraineeService(TraineeMapper traineeMapper, TraineeRepository traineeRepository,
			PasswordEncoder passwordEncoder) {
		this.traineeMapper = traineeMapper;
		this.traineeRepository = traineeRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void create(TraineeDto dto, String password) {
		Trainee trainee = traineeMapper.toEntity(dto);
		trainee.setPasswordHash(passwordEncoder.encode(password));
		traineeRepository.create(trainee);
	}

	@Override
	public void update(TraineeDto dto) {
		Trainee trainee = traineeRepository.findById(dto.userId())
				.orElseThrow(() -> new NoSuchEntityException("No trainee entity with id %d".formatted(dto.userId())));
		Trainee newTrainee = traineeMapper.toEntity(dto);
		newTrainee.setPasswordHash(trainee.getPasswordHash());
		traineeRepository.update(newTrainee);
	}

	@Override
	public void updatePassword(Long id, String password) {
		Trainee trainee = traineeRepository.findById(id)
				.orElseThrow(() -> new NoSuchEntityException("No trainee entity with id %d".formatted(id)));
		trainee.setPasswordHash(passwordEncoder.encode(password));
		traineeRepository.update(trainee);
	}

	@Override
	public void deleteById(Long id) {
		traineeRepository.deleteById(id);
	}

	@Override
	public Optional<TraineeDto> findById(Long id) {
		return traineeRepository.findById(id).map(traineeMapper::toDto);
	}

	@Override
	public Stream<TraineeDto> findAll() {
		return traineeRepository.findAll().map(traineeMapper::toDto);
	}

}
