package com.streamlined.tasks.parser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.streamlined.tasks.entity.Training;

@Component
public class TrainingParser extends Parser<Training.TrainingKey, Training> {

    public TrainingParser(CsvMapper csvMapper, @Value("${source.csv.training}") String sourceFileName) {
        super(Training.class, csvMapper, sourceFileName);
    }

}
