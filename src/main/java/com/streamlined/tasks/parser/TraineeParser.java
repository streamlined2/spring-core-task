package com.streamlined.tasks.parser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.streamlined.tasks.entity.Trainee;

@Component
public class TraineeParser extends Parser<Long, Trainee> {

	public TraineeParser(CsvMapper csvMapper, @Value("${source.csv.trainee}") String sourceFileName) {
		super(Trainee.class, csvMapper, sourceFileName);
	}

}
