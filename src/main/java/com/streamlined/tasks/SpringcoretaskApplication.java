package com.streamlined.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	public static void main(String[] args) {
		SpringApplication.run(SpringcoretaskApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(BCryptVersion.$2Y);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(traineeService.findAll().toList());
		System.out.println(trainerService.findAll().toList());
		System.out.println(trainingService.findAll().toList());
	}

}
