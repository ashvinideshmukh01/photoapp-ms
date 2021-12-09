package com.photoapp.userms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.photoapp.userms.data.AlbumServiceClient;
import com.photoapp.userms.data.UserEntity;
import com.photoapp.userms.shared.UserDTO;
import com.photoapp.userms.ui.model.AlbummResponseModel;
import com.photoapp.userms.userRepository.UserRepository;
@Service
public class UserServiceImpl<AlbumResponseModel> implements UserService {

	UserRepository userRepo;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	//RestTemplate restTemplate;
	AlbumServiceClient   AlbumServiceClient;
	Environment env;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
//	@Autowired
//	public UserServiceImpl(UserRepository userRepo,BCryptPasswordEncoder bCryptPasswordEncoder,	RestTemplate restTemplate,Environment env) {
//		this.userRepo = userRepo;
//		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
//		this.restTemplate=restTemplate;
//		this.env=env;
//	}
	
	@Autowired
	public UserServiceImpl(UserRepository userRepo,BCryptPasswordEncoder bCryptPasswordEncoder,	AlbumServiceClient   AlbumServiceClient,Environment env) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		this.AlbumServiceClient=AlbumServiceClient;
		this.env=env;
	}
	
	@Override
	public UserDTO createUser(UserDTO userdto) {
		userdto.setUserId(UUID.randomUUID().toString());
		
		ModelMapper modelmaper = new ModelMapper();
		modelmaper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelmaper.map(userdto, UserEntity.class);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userdto.getPassword()));
		UserEntity save = userRepo.save(userEntity);
		UserDTO dto = modelmaper.map(userEntity, UserDTO.class);
		return dto;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity= userRepo.findByEmail(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}
	@Override
	public UserDTO getUserByEmail(String email) {
		UserEntity userEntity= userRepo.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEntity, UserDTO.class);
	}
	@Override
	public UserDTO getUserByUserId(String userid) {
		
//		String albumUrl=String.format(env.getProperty("albums-ws-url"),userid);
//		
//		//String albumUrl=String.format(env.getProperty("albums-ws-url"),userid);
//		ResponseEntity<List<AlbumResponseModel>> albumsResponse= restTemplate.exchange(albumUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//		});
//		List<AlbumResponseModel> list = albumsResponse.getBody();
		String id = userid;
		List<AlbummResponseModel> list = AlbumServiceClient.getAlbums(id);
		UserEntity userEntity = userRepo.findByUserId(userid);
		if(userEntity==null) {
		throw new UsernameNotFoundException("User Not Found");
	}
		@SuppressWarnings("unchecked")
		UserDTO<AlbummResponseModel> userDTO = new ModelMapper().map(userEntity, UserDTO.class);
		userDTO.setAlbums(list);
		return userDTO;
	}

}
