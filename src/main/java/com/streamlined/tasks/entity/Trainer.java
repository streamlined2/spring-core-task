package com.streamlined.tasks.entity;

public class Trainer extends User {

	private String specialization;

	public Trainer() {
	}

	public Trainer(Long userId, String firstName, String lastName, String userName, String passwordHash,
			boolean isActive, String specialization) {
		super(userId, firstName, lastName, userName, passwordHash, isActive);
		this.specialization = specialization;
	}

	public Trainer(Long userId, String firstName, String lastName, String passwordHash, boolean isActive,
			String specialization) {
		super(userId, firstName, lastName, passwordHash, isActive);
		this.specialization = specialization;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	@Override
	public String toString() {
		return "Trainee{userId=%d, firstName=%s, lastName=%s, userName=%s, isActive=%b, specialization=%s}"
				.formatted(getUserId(), getFirstName(), getLastName(), getUserName(), isActive(), specialization);
	}

}
