package com.photoapp.userms.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoapp.userms.service.UserService;
import com.photoapp.userms.shared.UserDTO;
import com.photoapp.userms.ui.model.CreateUserReqModel;
import com.photoapp.userms.ui.model.CreateUserRespModel;
import com.photoapp.userms.ui.model.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	Environment env;
	@Autowired
	
	UserService myService;
	
	@GetMapping("/status/check")
    public String getStatus() {
		return "Welcome!!!!";
	}
	
//	@GetMapping("/status/check")
//    public String getStatus() {
//		return "Welcome!!!!"+env.getProperty("local.server.port");
//	}
//	@PostMapping("/login")
//    public String chkStatus() {
//		return "Welcome!!!!";
//	}
	@PostMapping
	public ResponseEntity createUser(@Valid @RequestBody CreateUserReqModel userDetails) {
		ModelMapper modelMap = new ModelMapper();
		UserDTO userDTO = modelMap.map(userDetails, UserDTO.class);
		UserDTO user = myService.createUser(userDTO);
		CreateUserRespModel userCreated = modelMap.map(userDTO, CreateUserRespModel.class);
		return new ResponseEntity<>(userCreated,HttpStatus.CREATED);
	}
	
	@GetMapping( value = "/{userid}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userid")String userid) {
  	UserDTO userDTO=myService.getUserByUserId(userid);
  	UserResponseModel returnValue = new ModelMapper().map(userDTO, UserResponseModel.class);
	return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
   	
    }
	

}
