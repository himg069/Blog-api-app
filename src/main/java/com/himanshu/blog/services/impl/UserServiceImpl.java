package com.himanshu.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.himanshu.blog.config.AppConstant;
import com.himanshu.blog.entities.Role;
import com.himanshu.blog.entities.Users;
import com.himanshu.blog.exceptions.NahiMilPaaRhaBhaiException;
import com.himanshu.blog.payload.UserDto;
import com.himanshu.blog.repositories.RoleRepo;
import com.himanshu.blog.repositories.UserRepo;
import com.himanshu.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		Users user = this.userDtoToUsers(userDto);
		Users saveUser = this.userRepo.save(user);
		return this.userToUserDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("User", "Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		Users user1 = this.userRepo.save(user);
		return this.userToUserDto(user1);
	}

	@Override
	public UserDto getUserById(Integer userId) {

		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("User", "Id", userId));

		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<Users> users = this.userRepo.findAll();
		List<UserDto> collect = users.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteUser(Integer userId) {
		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("User", "Id", userId));
		this.userRepo.delete(user);

	}

	private Users userDtoToUsers(UserDto userDto) {
		Users user = this.modelMapper.map(userDto, Users.class);
//		Users user = new Users();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;

	}

	private UserDto userToUserDto(Users user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
		Users user = this.modelMapper.map(userDto, Users.class);
		
		//encode the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		Role role = this.roleRepo.findById(AppConstant.READ_ONLY).get();
		user.getRoles().add(role);
		Users newUser = this.userRepo.save(user);
		UserDto updatedUserDto = this.modelMapper.map(newUser, UserDto.class);
		
		return updatedUserDto;
	}

}
