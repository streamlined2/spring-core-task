package com.streamlined.tasks.entity;

import java.time.LocalDate;

public class Trainee extends User {

	private LocalDate dateOfBirth;
	private String address;

	public Trainee() {
	}

	public Trainee(Long userId, String firstName, String lastName, String userName, String passwordHash,
			boolean isActive, LocalDate dateOfBirth, String address) {
		super(userId, firstName, lastName, userName, passwordHash, isActive);
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}

	public Trainee(Long userId, String firstName, String lastName, String passwordHash, boolean isActive,
			LocalDate dateOfBirth, String address) {
		super(userId, firstName, lastName, passwordHash, isActive);
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Trainee{userId=%d, firstName=%s, lastName=%s, userName=%s, isActive=%b, dateOfBirth=%tF, address=%s}"
				.formatted(getUserId(), getFirstName(), getLastName(), getUserName(), isActive(), dateOfBirth, address);
	}

}
