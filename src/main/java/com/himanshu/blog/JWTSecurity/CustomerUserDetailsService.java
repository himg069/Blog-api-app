package com.himanshu.blog.JWTSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.himanshu.blog.entities.Users;
import com.himanshu.blog.exceptions.NahiMilPaaRhaBhaiException;
import com.himanshu.blog.repositories.UserRepo;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users userData = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("User ", " email : " + username, 0));
		return userData;
	}

}
