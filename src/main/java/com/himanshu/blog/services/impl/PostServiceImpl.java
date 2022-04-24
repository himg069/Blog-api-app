package com.himanshu.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.himanshu.blog.entities.Category;
import com.himanshu.blog.entities.Post;
import com.himanshu.blog.entities.Users;
import com.himanshu.blog.exceptions.NahiMilPaaRhaBhaiException;
import com.himanshu.blog.payload.PostDto;
import com.himanshu.blog.payload.PostResponse;
import com.himanshu.blog.repositories.CategoryRepo;
import com.himanshu.blog.repositories.PostRepo;
import com.himanshu.blog.repositories.UserRepo;
import com.himanshu.blog.services.PostService;

import net.bytebuddy.asm.Advice.This;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo catRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer catId) {
		// TODO Auto-generated method stub

		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("User", "User Id", userId));

		Category cat = this.catRepo.findById(catId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("User", "User Id", userId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setPostDate(new Date());
		post.setUser(user);
		post.setCategory(cat);

		Post createPost = this.postRepo.save(post);

		return this.modelMapper.map(createPost, PostDto.class);
	}

	// manually created
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post existingPost = this.postRepo.findById(postId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("post", "post id", postId));
		existingPost.setTitle(postDto.getTitle());
		existingPost.setContent(postDto.getContent());
		existingPost.setImageName(postDto.getImageName());
		existingPost.setPostDate(postDto.getPostDate());
		Post updatedPost = this.postRepo.save(existingPost);
		PostDto updatedPostDto = this.modelMapper.map(updatedPost, PostDto.class);
		return updatedPostDto;
	}

	// manually created
	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post postToBeDeleted = this.postRepo.findById(postId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("Post", "Post Id", postId));
		this.postRepo.delete(postToBeDeleted);
	}

	// manually created
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub

		PageRequest pageing = PageRequest.of(pageNumber, pageSize);

//		List<Post> findAllPost = this.postRepo.findAll();
		Page<Post> pagePost = this.postRepo.findAll(pageing);
		List<Post> findAllPost = pagePost.getContent();
		List<PostDto> findAllPostDto = findAllPost.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(findAllPostDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getNumberOfElements());
		postResponse.setTotalPage(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	// manually created
	@Override
	public PostDto getOnePost(Integer postId) {
		// TODO Auto-generated method stub
		Post postById = this.postRepo.findById(postId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("Post", "Post id", postId));
		PostDto postDtoById = this.modelMapper.map(postById, PostDto.class);
		return postDtoById;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer catId) {
		// TODO Auto-generated method stub
		Category cat = this.catRepo.findById(catId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("category", "category Id", catId));

		List<Post> listOfPostByCategory = this.postRepo.findAllByCategory(cat);
		List<PostDto> listOfPostDtoByCat = listOfPostByCategory.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return listOfPostDtoByCat;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		// TODO Auto-generated method stub
		Users userInfo = this.userRepo.findById(userId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("User", "user id", userId));
		List<Post> findAllPostByUser = this.postRepo.findAllByUser(userInfo);
		List<PostDto> findAllPostDtoByUser = findAllPostByUser.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return findAllPostDtoByUser;
	}

	@Override
	public List<Post> searchPost(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
}