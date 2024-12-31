package com.streamlined.tasks.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.parser.TraineeParser;

@Component
public class HashMapTraineeStorage extends HashMapStorage<Long, Trainee> {

    public HashMapTraineeStorage() {
        super();
    }

    @Autowired
    public HashMapTraineeStorage(TraineeParser traineeParser) {
        super(traineeParser);
    }

}
