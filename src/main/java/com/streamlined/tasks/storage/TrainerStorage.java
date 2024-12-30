package com.streamlined.tasks.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.parser.TrainerParser;

import jakarta.annotation.PostConstruct;

@Component
public class TrainerStorage {

    private final Map<Long, Trainer> trainerMap;
    private TrainerParser trainerParser;

    public TrainerStorage() {
        trainerMap = new HashMap<>();
    }

    @Autowired
    public TrainerStorage(TrainerParser trainerParser) {
        this();
        this.trainerParser = trainerParser;
    }

    @PostConstruct
    private void initialize() {
        trainerMap.putAll(trainerParser.parse());
    }

    public Trainer saveNew(Trainer trainer) {
        return trainerMap.putIfAbsent(trainer.getUserId(), trainer);
    }

    public void save(Long id, Trainer trainer) {
        trainerMap.put(id, trainer);
    }

    public Trainer get(Long id) {
        return trainerMap.get(id);
    }

    public void remove(Long id) {
        trainerMap.remove(id);
    }

    public Stream<Trainer> getAll() {
        return trainerMap.values().stream();
    }

    public void clear() {
        trainerMap.clear();
    }

    public int size() {
        return trainerMap.size();
    }

}
