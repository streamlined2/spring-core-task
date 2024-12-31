package com.streamlined.tasks.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.parser.TrainingParser;

@Component
public class HashMapTrainingStorage extends HashMapStorage<Training.TrainingKey, Training> {

    public HashMapTrainingStorage() {
        super();
    }

    @Autowired
    public HashMapTrainingStorage(TrainingParser trainingParser) {
        super(trainingParser);
    }

}
