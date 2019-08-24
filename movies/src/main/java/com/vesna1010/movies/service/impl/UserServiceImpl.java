package com.vesna1010.movies.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vesna1010.movies.dao.UserDao;
import com.vesna1010.movies.model.User;
import com.vesna1010.movies.service.UserService;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SessionRegistry sessionRegistry;

	@Override
	public List<User> findAllUsers() {
		return userDao.findAllUsers();
	}

	@Override
	public List<User> findLoggedUsers() {
		List<User> loggedUsers = new ArrayList<User>();

		for (String username : findPrincipalUsernames()) {
			loggedUsers.add(userDao.findUserByUsername(username));
		}

		return (List<User>) loggedUsers;
	}

	private Set<String> findPrincipalUsernames() {
		Set<String> usernames = new HashSet<>();

		for (Object obj : sessionRegistry.getAllPrincipals()) {
			if (obj instanceof org.springframework.security.core.userdetails.User) {
				usernames.add(((org.springframework.security.core.userdetails.User) obj).getUsername());
			}
		}

		return usernames;
	}

	@Override
	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}

	@Override
	public boolean existsUserByUsername(String username) {
		return userDao.existsUserByUsername(username);
	}

	@Override
	public void saveUser(User user) {
		user = setUserPassword(user);
		userDao.saveUser(user);
	}

	private User setUserPassword(User user) {
		String password = user.getPassword();

		password = encodedPassword(password);
		user.setPassword(password);

		return user;
	}

	private String encodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public void resetUserVotes(User user) {
		user.setVote(false);
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

	@Override
	public void disableUser(User user) {
		user.setEnabled(false);
		userDao.updateUser(user);
	}

}
