package com.streamlined.tasks.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.parser.Parser;

@Component
public class HashMapTraineeStorage extends HashMapStorage<Long, Trainee> {

    public HashMapTraineeStorage() {
        super();
    }

    @Autowired
    public HashMapTraineeStorage(@Qualifier("traineeParser") Parser<Long, Trainee> parser) {
        super(parser);
    }

}
