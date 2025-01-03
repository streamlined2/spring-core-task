package com.streamlined.tasks;

import java.time.LocalDate;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.service.TraineeService;
import com.streamlined.tasks.service.TrainerService;
import com.streamlined.tasks.service.TrainingService;

@Configuration
@ComponentScan("com.streamlined.tasks")
@PropertySource("classpath:application.properties")
public class SpringcoretaskApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringcoretaskApplication.class);

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

}
