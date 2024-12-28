package com.streamlined.tasks.service;

public interface SecurityService {

	char[] getNewPassword();
	String getPasswordHash(char[] password);

}
