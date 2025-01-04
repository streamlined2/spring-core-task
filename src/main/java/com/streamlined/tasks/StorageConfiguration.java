package com.streamlined.tasks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.storage.InMemoryStorage;

@Configuration
public class StorageConfiguration {

    @Bean
    CsvMapper csvMapper() {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.registerModule(new JavaTimeModule());
        return csvMapper;
    }

    @Bean("traineeStorage")
    InMemoryStorage<Long, Trainee> traineeStorage(@Value("${source.csv.trainee}") String sourceFileName) {
        return new InMemoryStorage<>();
    }

    @Bean("trainerStorage")
    InMemoryStorage<Long, Trainer> trainerStorage(@Value("${source.csv.trainer}") String sourceFileName) {
        return new InMemoryStorage<>();
    }

    @Bean("trainingStorage")
    InMemoryStorage<Training.TrainingKey, Training> trainingStorage(
            @Value("${source.csv.training}") String sourceFileName) {
        return new InMemoryStorage<>();
    }

}
