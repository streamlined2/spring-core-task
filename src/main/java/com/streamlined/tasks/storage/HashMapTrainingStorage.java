package com.streamlined.tasks.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.parser.Parser;

@Component
public class HashMapTrainingStorage extends HashMapStorage<Training.TrainingKey, Training> {

    public HashMapTrainingStorage() {
        super();
    }

    @Autowired
    public HashMapTrainingStorage(@Qualifier("trainingParser") Parser<Training.TrainingKey, Training> parser) {
        super(parser);
    }

}
