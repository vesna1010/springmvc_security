package com.vesna1010.movies.dao;

import java.util.List;
import com.vesna1010.movies.model.User;

public interface UserDao {

	List<User> findAllUsers();

	User findUserByUsername(String username);

	boolean existsUserByUsername(String username);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUser(User user);

}
