package com.himanshu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himanshu.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
