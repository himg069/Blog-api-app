package com.himanshu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himanshu.blog.entities.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {

}
