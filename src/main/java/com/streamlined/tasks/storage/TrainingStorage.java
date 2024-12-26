package com.streamlined.tasks.storage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.entity.TrainingType;

import jakarta.annotation.PostConstruct;

@Component
public class TrainingStorage {

	private final Map<Training.TrainingKey, Training> trainingMap;

	public TrainingStorage() {
		trainingMap = new HashMap<>();
	}

	@PostConstruct
	private void initialize() {
		final TrainingType artType = new TrainingType("Art");
		final TrainingType mathType = new TrainingType("Math");
		final TrainingType computerType = new TrainingType("Computer");

		List<Training> trainingList = List.of(
				new Training(1L, 1L, "Math training", mathType, LocalDate.of(2020, 1, 1), Duration.ofDays(20)),
				new Training(2L, 1L, "Math training", mathType, LocalDate.of(2021, 1, 1), Duration.ofDays(20)),
				new Training(3L, 1L, "Math training", mathType, LocalDate.of(2022, 1, 1), Duration.ofDays(20)),
				new Training(3L, 2L, "Art training", artType, LocalDate.of(2020, 1, 1), Duration.ofDays(60)),
				new Training(4L, 2L, "Art training", artType, LocalDate.of(2021, 1, 1), Duration.ofDays(60)),
				new Training(5L, 2L, "Art training", artType, LocalDate.of(2022, 1, 1), Duration.ofDays(60)),
				new Training(1L, 3L, "Computer science training", computerType, LocalDate.of(2020, 1, 1),
						Duration.ofDays(120)),
				new Training(3L, 3L, "Computer science training", computerType, LocalDate.of(2021, 1, 1),
						Duration.ofDays(120)),
				new Training(5L, 3L, "Computer science training", computerType, LocalDate.of(2022, 1, 1),
						Duration.ofDays(120)));

		for (Training training : trainingList) {
			trainingMap.put(training.getTrainingKey(), training);
		}
	}

	public Training saveNew(Training training) {
		return trainingMap.putIfAbsent(training.getTrainingKey(), training);
	}

	public void save(TrainingKey id, Training training) {
		trainingMap.put(id, training);
	}

	public Training get(TrainingKey id) {
		return trainingMap.get(id);
	}

	public Stream<Training> getAll() {
		return trainingMap.values().stream();
	}

}
