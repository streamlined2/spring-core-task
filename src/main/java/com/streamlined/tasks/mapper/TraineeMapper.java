package com.streamlined.tasks.mapper;

import org.springframework.stereotype.Component;

import com.streamlined.tasks.dto.TraineeDto;
import com.streamlined.tasks.entity.Trainee;

@Component
public class TraineeMapper {

	public Trainee toEntity(TraineeDto dto) {
		return new Trainee(dto.userId(), dto.firstName(), dto.lastName(), dto.userName(), null, dto.isActive(),
				dto.dateOfBirth(), dto.address());
	}

	public TraineeDto toDto(Trainee entity) {
		return new TraineeDto(entity.getUserId(), entity.getFirstName(), entity.getLastName(), entity.getUserName(),
				entity.isActive(), entity.getDateOfBirth(), entity.getAddress());
	}

}
