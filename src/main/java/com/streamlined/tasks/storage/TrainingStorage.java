package com.streamlined.tasks.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.entity.Training.TrainingKey;
import com.streamlined.tasks.parser.TrainingParser;
import jakarta.annotation.PostConstruct;

@Component
public class TrainingStorage {

	private final Map<Training.TrainingKey, Training> trainingMap;
	private TrainingParser trainingParser;

	public TrainingStorage() {
		trainingMap = new HashMap<>();
	}

	@Autowired
	public TrainingStorage(TrainingParser trainingParser) {
		this();
		this.trainingParser = trainingParser;
	}

	@PostConstruct
	private void initialize() {
		trainingMap.putAll(trainingParser.parse());
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

	public void clear() {
		trainingMap.clear();
	}

	public int size() {
		return trainingMap.size();
	}

}
