package com.streamlined.tasks.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

public class Training implements Entity<com.streamlined.tasks.entity.Training.TrainingKey> {

	public record TrainingKey(Long traineeId, Long trainerId, LocalDate date) {
	}

	private Long traineeId;
	private Long trainerId;
	private String name;
	private TrainingType type;
	private LocalDate date;
	private Duration duration;

	public Training() {
	}

	public Training(Long traineeId, Long trainerId, String name, TrainingType type, LocalDate date, Duration duration) {
		this.traineeId = traineeId;
		this.trainerId = trainerId;
		this.name = name;
		this.type = type;
		this.date = date;
		this.duration = duration;
	}

	public TrainingKey getTrainingKey() {
		return new TrainingKey(traineeId, trainerId, date);
	}

	public Long getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(Long traineeId) {
		this.traineeId = traineeId;
	}

	public Long getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrainingType getType() {
		return type;
	}

	public void setType(TrainingType type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, traineeId, trainerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Training training) {
			return Objects.equals(traineeId, training.traineeId) && Objects.equals(trainerId, training.trainerId)
					&& Objects.equals(date, training.date);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Training{traineeId=%d, trainerId=%d, name=%s, type=%s, date=%tF, duration=%s}".formatted(traineeId,
				trainerId, name, type.getName(), date, duration.toString());
	}

	@Override
	public TrainingKey getPrimaryKey() {
		return getTrainingKey();
	}

}
