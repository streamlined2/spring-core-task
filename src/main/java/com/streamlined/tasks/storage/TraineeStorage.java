package com.streamlined.tasks.storage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainee;

import jakarta.annotation.PostConstruct;

@Component
public class TraineeStorage {

	private final Map<Long, Trainee> traineeMap;
	private final PasswordEncoder passwordEncoder;

	public TraineeStorage(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		traineeMap = new HashMap<>();
	}

	@PostConstruct
	private void initilialize() {
		List<Trainee> traineeList = List.of(
				new Trainee(1L, "John", "Smith", "John.Smith", passwordEncoder.encode("john"), true,
						LocalDate.of(1990, 1, 1), "USA"),
				new Trainee(2L, "Jack", "Powell", "Jack.Powell", passwordEncoder.encode("jack"), true,
						LocalDate.of(1992, 2, 15), "UK"),
				new Trainee(3L, "Robert", "Orwell", "Robert.Orwell", passwordEncoder.encode("robert"), true,
						LocalDate.of(1991, 3, 10), "UK"),
				new Trainee(4L, "Ingrid", "Kin", "Ingrid.Kin", passwordEncoder.encode("ingrid"), true,
						LocalDate.of(1989, 4, 12), "Norway"),
				new Trainee(5L, "Kyle", "Stark", "Kyle.Stark", passwordEncoder.encode("kyle"), true,
						LocalDate.of(1988, 5, 18), "USA"));
		for (Trainee trainee : traineeList) {
			traineeMap.put(trainee.getUserId(), trainee);
		}
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
