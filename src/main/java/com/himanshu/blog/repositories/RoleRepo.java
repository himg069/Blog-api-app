package com.himanshu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himanshu.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
