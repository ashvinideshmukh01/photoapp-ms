package com.photoapp.userms.shared;

import java.io.Serializable;
import java.util.List;

import com.photoapp.userms.ui.model.AlbummResponseModel;

public class UserDTO<AlbumResponseModel> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4437961085135329314L;
	
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String encryptedPassword;
	private String userId;
	List<AlbummResponseModel> albums;
	
	
	
	public List<AlbummResponseModel> getAlbums() {
		return albums;
	}
	public void setAlbums(List<AlbummResponseModel> list) {
		this.albums = list;
	}
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
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	 	
	

}
