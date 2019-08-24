package com.vesna1010.movies.service;

import java.util.List;
import com.vesna1010.movies.model.User;

public interface UserService {

	List<User> findAllUsers();

	List<User> findLoggedUsers();

	User findUserByUsername(String username);

	boolean existsUserByUsername(String username);

	void saveUser(User user);

	void resetUserVotes(User user);

	void deleteUser(User user);

	void disableUser(User user);

}
