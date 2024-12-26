package com.streamlined.tasks.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.exception.NoSuchEntityException;
import com.streamlined.tasks.mapper.TrainerMapper;
import com.streamlined.tasks.repository.TrainerRepository;

@Service
public class DefaultTrainerService implements TrainerService {

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
		Trainer trainer = trainerMapper.toEntity(dto);
		trainer.setPasswordHash(passwordEncoder.encode(password));
		trainerRepository.create(trainer);
	}

	@Override
	public void update(TrainerDto dto) {
		Trainer trainer = trainerRepository.findById(dto.userId())
				.orElseThrow(() -> new NoSuchEntityException("No trainer entity with id %d".formatted(dto.userId())));
		Trainer newTrainer = trainerMapper.toEntity(dto);
		newTrainer.setPasswordHash(trainer.getPasswordHash());
		trainerRepository.update(newTrainer);
	}

	@Override
	public void updatePassword(Long id, String password) {
		Trainer trainer = trainerRepository.findById(id)
				.orElseThrow(() -> new NoSuchEntityException("No trainer entity with id %d".formatted(id)));
		trainer.setPasswordHash(passwordEncoder.encode(password));
		trainerRepository.update(trainer);
	}

	@Override
	public void deleteById(Long id) {
		trainerRepository.deleteById(id);
	}

	@Override
	public Optional<TrainerDto> findById(Long id) {
		return trainerRepository.findById(id).map(trainerMapper::toDto);
	}

	@Override
	public Stream<TrainerDto> findAll() {
		return trainerRepository.findAll().map(trainerMapper::toDto);
	}

}
