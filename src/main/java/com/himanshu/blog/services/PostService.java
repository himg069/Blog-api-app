package com.himanshu.blog.services;

import java.util.List;

import com.himanshu.blog.entities.Post;
import com.himanshu.blog.payload.PostDto;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer catId);

	PostDto updatePost(PostDto postDto, Integer postId);

	void deletePost(Integer postId);

	List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);

	PostDto getOnePost(Integer postId);

	List<PostDto> getPostByCategory(Integer catId);

	List<PostDto> getPostByUser(Integer userId);

	List<Post> searchPost(String keyword);
}