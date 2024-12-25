package com.streamlined.tasks.entity;

import java.util.Objects;

public class TrainingType {

	private String name;

	public TrainingType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrainingType type) {
			return Objects.equals(name, type.name);
		}
		return false;
	}

	@Override
	public String toString() {
		return "TrainingType{name=%s}".formatted(name);
	}

}
