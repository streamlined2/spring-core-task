package com.streamlined.tasks.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.parser.TrainerParser;

@Component
public class HashMapTrainerStorage extends HashMapStorage<Long, Trainer> {

    public HashMapTrainerStorage() {
        super();
    }

    @Autowired
    public HashMapTrainerStorage(TrainerParser trainerParser) {
        super(trainerParser);
    }

}
