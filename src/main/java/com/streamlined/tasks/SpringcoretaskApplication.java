package com.streamlined.tasks;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.entity.Trainee;
import com.streamlined.tasks.entity.Trainer;
import com.streamlined.tasks.entity.Training;
import com.streamlined.tasks.exception.MissingAlgorithmException;
import com.streamlined.tasks.parser.Parser;
import com.streamlined.tasks.service.TraineeService;
import com.streamlined.tasks.service.TrainerService;
import com.streamlined.tasks.service.TrainingService;
import com.streamlined.tasks.storage.HashMapStorage;

@Configuration
@ComponentScan("com.streamlined.tasks")
@PropertySource("classpath:application.properties")
public class SpringcoretaskApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringcoretaskApplication.class);

    private @Value("${algorithm.random}") String algorithmName;

    public static void main(String[] args) {
        new SpringcoretaskApplication().run();
    }

    void run() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringcoretaskApplication.class)) {
            TraineeService traineeService = context.getBean(TraineeService.class);
            TrainerService trainerService = context.getBean(TrainerService.class);
            TrainingService trainingService = context.getBean(TrainingService.class);

            TraineeDto dto = new TraineeDto(100L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1),
                    "");
            traineeService.create(dto, "pass".toCharArray());
            dto = new TraineeDto(101L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "");
            traineeService.create(dto, "pass".toCharArray());
            dto = new TraineeDto(102L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "");
            traineeService.create(dto, "pass".toCharArray());

            TrainerDto trainerDto = new TrainerDto(103L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");
            trainerService.create(trainerDto, "pass".toCharArray());
            trainerDto = new TrainerDto(104L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");
            trainerService.create(trainerDto, "pass".toCharArray());
            trainerDto = new TrainerDto(105L, "Jack", "Fantasy", "Jack.Fantasy", true, "Math");
            trainerService.create(trainerDto, "pass".toCharArray());

            traineeService.findAll().map(Objects::toString).forEach(LOGGER::info);
            trainerService.findAll().map(Objects::toString).forEach(LOGGER::info);
            trainingService.findAll().map(Objects::toString).forEach(LOGGER::info);

        } catch (Exception e) {
            LOGGER.error("Error while executing application: {}", e.getMessage(), e);
        }
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptVersion.$2Y);
    }

    @Bean
    CsvMapper csvMapper() {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.registerModule(new JavaTimeModule());
        return csvMapper;
    }

    @Bean
    Random random() {
        try {
            return SecureRandom.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Missing random generator algorithm {}", algorithmName, e);
            throw new MissingAlgorithmException("Missing random generator algorithm", e);
        }
    }

    @Bean("traineeParser")
    Parser<Long, Trainee> traineeParser(@Value("${source.csv.trainee}") String sourceFileName) {
        return new Parser<>(Trainee.class, csvMapper(), sourceFileName);
    }

    @Bean("trainerParser")
    Parser<Long, Trainer> trainerParser(@Value("${source.csv.trainer}") String sourceFileName) {
        return new Parser<>(Trainer.class, csvMapper(), sourceFileName);
    }

    @Bean("trainingParser")
    Parser<Training.TrainingKey, Training> trainingParser(@Value("${source.csv.training}") String sourceFileName) {
        return new Parser<>(Training.class, csvMapper(), sourceFileName);
    }

    @Bean("traineeStorage")
    HashMapStorage<Long, Trainee> traineeStorage(@Value("${source.csv.trainee}") String sourceFileName) {
        return new HashMapStorage<>(traineeParser(sourceFileName));
    }

    @Bean("trainerStorage")
    HashMapStorage<Long, Trainer> trainerStorage(@Value("${source.csv.trainer}") String sourceFileName) {
        return new HashMapStorage<>(trainerParser(sourceFileName));
    }

    @Bean("trainingStorage")
    HashMapStorage<Training.TrainingKey, Training> trainingStorage(
            @Value("${source.csv.training}") String sourceFileName) {
        return new HashMapStorage<>(trainingParser(sourceFileName));
    }

}
