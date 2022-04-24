package com.himanshu.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.himanshu.blog.entities.Category;
import com.himanshu.blog.entities.Post;
import com.himanshu.blog.entities.Users;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findAllByUser(Users user);
	List<Post> findAllByCategory(Category category);

	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
}
