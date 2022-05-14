package com.himanshu.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himanshu.blog.JWTSecurity.JWTTokenHelper;
import com.himanshu.blog.exceptions.APIException;
import com.himanshu.blog.payload.JWTAuthenticationRequest;
import com.himanshu.blog.payload.JWTAuthenticationResponse;
import com.himanshu.blog.payload.UserDto;
import com.himanshu.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class JWTAuthenticationController {

	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthenticationResponse> createToken(@RequestBody JWTAuthenticationRequest request)
			throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails loadUserByUsername = this.userDetailsService.loadUserByUsername(request.getUsername());
		String generatedToken = this.jwtTokenHelper.generateToken(loadUserByUsername);
		JWTAuthenticationResponse response = new JWTAuthenticationResponse();
		response.setToken(generatedToken);
		return new ResponseEntity<JWTAuthenticationResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("Credentials are not valid !!");
			throw new APIException("Invalid username or password !!");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.OK);
	}

}
