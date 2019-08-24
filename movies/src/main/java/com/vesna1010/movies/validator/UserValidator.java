package com.vesna1010.movies.validator;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.vesna1010.movies.model.Authority;
import com.vesna1010.movies.model.User;
import com.vesna1010.movies.service.UserService;

@Component
public class UserValidator implements Validator {

	public static final String USERNAME_REGEX = "^\\w{8,15}$";
	public static final String PASSWORD_REGEX = "^\\S{8,15}$";

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> cls) {
		return cls.equals(User.class);
	}

	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;

		if (userService.existsUserByUsername(user.getUsername())) {
			errors.rejectValue("username", "user.exists");
		}

		if (isInvalidUsername(user.getUsername())) {
			errors.rejectValue("username", "user.username");
		}

		if (isInvalidPassword(user.getPassword())) {
			errors.rejectValue("password", "user.password");
		}

		if (isInvalidAuthorities(user.getAuthorities())) {
			errors.rejectValue("authorities", "user.authorities");
		}
	}

	private boolean isInvalidUsername(String username) {
		return (username == null || !username.matches(USERNAME_REGEX));
	}

	public boolean isInvalidPassword(String password) {
		return (password == null || !password.matches(PASSWORD_REGEX));
	}

	public boolean isInvalidAuthorities(Set<Authority> authorities) {
		return (authorities == null || authorities.isEmpty());
	}

}
