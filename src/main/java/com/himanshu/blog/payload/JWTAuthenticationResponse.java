package com.himanshu.blog.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JWTAuthenticationResponse {

	private String token;
}
