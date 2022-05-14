package com.himanshu.blog.services;

import java.util.List;

import com.himanshu.blog.payload.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUser();

	void deleteUser(Integer userId);
	
	UserDto registerNewUser(UserDto user);
}
