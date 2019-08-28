package com.vesna1010.movies.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import com.vesna1010.movies.model.User;
import com.vesna1010.movies.service.UserService;

public class UserConverter implements Converter<String, User> {

	@Autowired
	private UserService userService;

	@Override
	public User convert(String username) {
		return userService.findUserByUsername(username);
	}

}
