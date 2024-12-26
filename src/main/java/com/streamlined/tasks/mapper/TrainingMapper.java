package com.streamlined.tasks.mapper;

import org.springframework.stereotype.Component;

import com.streamlined.tasks.dto.TrainingDto;
import com.streamlined.tasks.entity.Training;

@Component
public class TrainingMapper {

	public Training toEntity(TrainingDto dto) {
		return new Training(dto.traineeId(), dto.trainerId(), dto.name(), dto.type(), dto.date(), dto.duration());
	}

	public TrainingDto toDto(Training entity) {
		return new TrainingDto(entity.getTraineeId(), entity.getTrainerId(), entity.getName(), entity.getType(),
				entity.getDate(), entity.getDuration());
	}

}
