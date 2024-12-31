package com.streamlined.tasks.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.parser.TraineeParser;

import jakarta.annotation.PostConstruct;

@Component
public class HashMapTraineeStorage {

    private final Map<Long, Trainee> traineeMap;
    private TraineeParser traineeParser;

    public HashMapTraineeStorage() {
        traineeMap = new HashMap<>();
    }

    @Autowired
    public HashMapTraineeStorage(TraineeParser traineeParser) {
        this();
        this.traineeParser = traineeParser;
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

    public void clear() {
        traineeMap.clear();
    }

    public int size() {
        return traineeMap.size();
    }

}
