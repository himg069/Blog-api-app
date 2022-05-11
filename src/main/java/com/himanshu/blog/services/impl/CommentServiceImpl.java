package com.himanshu.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himanshu.blog.entities.Comment;
import com.himanshu.blog.entities.Post;
import com.himanshu.blog.exceptions.NahiMilPaaRhaBhaiException;
import com.himanshu.blog.payload.CommentDto;
import com.himanshu.blog.payload.PostDto;
import com.himanshu.blog.repositories.CommentRepo;
import com.himanshu.blog.repositories.PostRepo;
import com.himanshu.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("Post", "PostId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comm = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new NahiMilPaaRhaBhaiException("Comment", "CommentId", commentId));
		this.commentRepo.delete(comm);
	}

}
