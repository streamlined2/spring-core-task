package com.streamlined.tasks;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.service.TraineeService;
import com.streamlined.tasks.service.TrainerService;
import com.streamlined.tasks.service.TrainingService;

@SpringBootApplication
public class SpringcoretaskApplication implements CommandLineRunner {

	@Autowired
	@Lazy
	private TraineeService traineeService;
	@Autowired
	@Lazy
	private TrainerService trainerService;
	@Autowired
	@Lazy
	private TrainingService trainingService;

	private static final Logger log = LoggerFactory.getLogger(SpringcoretaskApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringcoretaskApplication.class, args);
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

	@Override
	public void run(String... args) throws Exception {
		try {
			TraineeDto dto = new TraineeDto(100L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1),
					"");
			traineeService.create(dto, "pass".toCharArray());
			dto = new TraineeDto(101L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "");
			traineeService.create(dto, "pass".toCharArray());
			dto = new TraineeDto(102L, "Jack", "Fantasy", "Jack.Fantasy", true, LocalDate.of(2000, 1, 1), "");
			traineeService.create(dto, "pass".toCharArray());

			traineeService.findAll().map(Objects::toString).forEach(log::info);
			trainerService.findAll().map(Objects::toString).forEach(log::info);
			trainingService.findAll().map(Objects::toString).forEach(log::info);

		} catch (Exception e) {
			log.error("Error while executing application: {}", e.getMessage(), e);
		}

	}

}
