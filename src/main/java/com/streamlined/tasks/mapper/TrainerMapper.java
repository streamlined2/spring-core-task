package com.streamlined.tasks.mapper;

import org.springframework.stereotype.Component;

import com.streamlined.tasks.dto.TrainerDto;
import com.streamlined.tasks.entity.Trainer;

@Component
public class TrainerMapper {

    public Trainer toEntity(TrainerDto dto) {
        return new Trainer(dto.userId(), dto.firstName(), dto.lastName(), dto.userName(), dto.isActive(),
                dto.specialization());
    }

    public TrainerDto toDto(Trainer entity) {
        return new TrainerDto(entity.getUserId(), entity.getFirstName(), entity.getLastName(), entity.getUserName(),
                entity.isActive(), entity.getSpecialization());
    }

}
