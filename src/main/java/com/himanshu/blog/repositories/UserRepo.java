package com.himanshu.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himanshu.blog.entities.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {

	Optional<Users> findByEmail(String email);
}
