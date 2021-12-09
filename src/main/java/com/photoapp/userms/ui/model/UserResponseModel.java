package com.photoapp.userms.ui.model;

import java.util.List;



public class UserResponseModel {

	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private List<AlbummResponseModel> albums;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String userName) {
		this.firstName = userName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AlbummResponseModel> getAlbums() {
		return albums;
	}
	public void setAlbums(List<AlbummResponseModel> albums) {
		this.albums = albums;
	}
	
	

}
