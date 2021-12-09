package com.photoapp.userms.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.photoapp.userms.shared.UserDTO;
import com.photoapp.userms.ui.model.AlbummResponseModel;

public interface UserService extends UserDetailsService {
	public UserDTO createUser(UserDTO userdto);
	public UserDTO getUserByEmail(String email);
	public UserDTO getUserByUserId(String userid);
}
