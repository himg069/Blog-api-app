package com.himanshu.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.himanshu.blog.payload.ApiResponse;
import com.himanshu.blog.payload.PostDto;
import com.himanshu.blog.payload.PostResponse;
import com.himanshu.blog.services.FileService;
import com.himanshu.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	private static final Logger log = LogManager.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/users/{userId}/categories/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		log.info("I am in createPost controller");
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	@RequestMapping("/users/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		log.info("I am in getPostByUser controller");
		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser, HttpStatus.OK);
	}

	@RequestMapping("/categories/{catId}/post")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer catId) {
		log.info("I am in getPostByCategory controller");
		List<PostDto> postByCat = this.postService.getPostByCategory(catId);
		return new ResponseEntity<List<PostDto>>(postByCat, HttpStatus.OK);
	}

	// manually created
	@GetMapping("/post")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		log.info("I am in getAllPost controller");
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);
	}

	// manually created
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getOnePost(@PathVariable Integer postId) {
		log.info("I am in getOnePost controller");
		PostDto onePost = this.postService.getOnePost(postId);
		return new ResponseEntity<PostDto>(onePost, HttpStatus.OK);
	}

	// manually created
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		log.info("I am in deletePost controller");
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted successfully !!", true), HttpStatus.OK);
	}

	// manually created
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updateExistingPost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable("keyword") String keyword) {
		List<PostDto> searchPost = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(searchPost, HttpStatus.OK);
	}

	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException {

		PostDto onePost = this.postService.getOnePost(postId);
		String uploadImage = this.fileService.uploadImage(path, image);
		onePost.setImageName(uploadImage);
		PostDto updatePost = this.postService.updatePost(onePost, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
