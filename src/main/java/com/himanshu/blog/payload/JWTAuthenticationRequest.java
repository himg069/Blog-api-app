package com.himanshu.blog.payload;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {

	private String username;
	
	private String password;
}
