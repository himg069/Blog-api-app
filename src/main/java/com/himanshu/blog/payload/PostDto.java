package com.himanshu.blog.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.himanshu.blog.entities.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date postDate;

	private CategoryDto category;

	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();
}
