package com.streamlined.tasks.parser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.streamlined.tasks.entity.Trainer;

@Component
public class TrainerParser extends Parser<Long, Trainer> {

    public TrainerParser(CsvMapper csvMapper, @Value("${source.csv.trainer}") String sourceFileName) {
        super(Trainer.class, csvMapper, sourceFileName);
    }

}
