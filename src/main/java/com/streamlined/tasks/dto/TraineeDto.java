package com.streamlined.tasks.dto;

import java.time.LocalDate;

public record TraineeDto(Long userId, String firstName, String lastName, String userName, boolean isActive,
		LocalDate dateOfBirth, String address) {

}
