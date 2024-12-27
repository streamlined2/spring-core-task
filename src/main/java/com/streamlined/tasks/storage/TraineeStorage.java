package com.streamlined.tasks.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.parser.TraineeParser;

import jakarta.annotation.PostConstruct;

@Component
public class TraineeStorage {

	private final Map<Long, Trainee> traineeMap;
	private final PasswordEncoder passwordEncoder;
	private final TraineeParser traineeParser;

	public TraineeStorage(PasswordEncoder passwordEncoder, TraineeParser traineeParser) {
		this.passwordEncoder = passwordEncoder;
		this.traineeParser = traineeParser;
		traineeMap = new HashMap<>();
	}

	@PostConstruct
	private void initilialize() {
		traineeMap.putAll(traineeParser.parse());
	}

	public Trainee saveNew(Trainee trainee) {
		return traineeMap.putIfAbsent(trainee.getUserId(), trainee);
	}

	public void save(Long id, Trainee trainee) {
		traineeMap.put(id, trainee);
	}

	public Trainee get(Long id) {
		return traineeMap.get(id);
	}

	public void remove(Long id) {
		traineeMap.remove(id);
	}

	public Stream<Trainee> getAll() {
		return traineeMap.values().stream();
	}

}
