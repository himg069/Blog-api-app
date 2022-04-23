package com.himanshu.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.himanshu.blog.entities.Post;
import com.himanshu.blog.payload.ApiResponse;
import com.himanshu.blog.payload.PostDto;
import com.himanshu.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("/users/{userId}/categories/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	@RequestMapping("/users/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser, HttpStatus.OK);
	}

	@RequestMapping("/categories/{catId}/post")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer catId) {
		List<PostDto> postByCat = this.postService.getPostByCategory(catId);
		return new ResponseEntity<List<PostDto>>(postByCat, HttpStatus.OK);
	}

	// manually created
	@GetMapping("/post")
	public ResponseEntity<List<PostDto>> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
		List<PostDto> allPost = this.postService.getAllPost(pageNumber,pageSize);
		return new ResponseEntity<List<PostDto>>(allPost, HttpStatus.OK);
	}

	// manually created
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getOnePost(@PathVariable Integer postId) {
		PostDto onePost = this.postService.getOnePost(postId);
		return new ResponseEntity<PostDto>(onePost, HttpStatus.OK);
	}

	// manually created
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted successfully !!", true), HttpStatus.OK);
	}

	// manually created
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updateExistingPost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
}
