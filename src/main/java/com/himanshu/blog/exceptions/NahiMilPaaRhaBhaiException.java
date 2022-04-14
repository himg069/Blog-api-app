package com.himanshu.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NahiMilPaaRhaBhaiException extends RuntimeException {

	private String userName;
	private String id;
	private long userId;

	public NahiMilPaaRhaBhaiException(String userName, String id, long userId) {
		super(String.format("%s naam ka aur %s id ka banda, ni mil rha bhai. %s sorry bhai", userName, id, userId));
		this.userName = userName;
		this.id = id;
		this.userId = userId;
	}

}
