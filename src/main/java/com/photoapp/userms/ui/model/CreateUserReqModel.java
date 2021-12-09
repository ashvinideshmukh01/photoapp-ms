package com.photoapp.userms.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserReqModel {
	
	@NotNull(message="First name can not be null")
	@Size(min = 2)
	private String firstName;
	
	@NotNull(message="last name can not be null")
	@Size(min = 2)
	private String lastName;
	
	@NotNull(message="password  can not be null")
	@Size(min = 8, max = 20, message="password should be greater than 8 chars and less than 20 chars")
	private String password;
	
	@NotNull(message="email should not be null")
	@Email
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
