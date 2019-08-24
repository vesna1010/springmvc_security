package com.vesna1010.movies.test.dao;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vesna1010.movies.dao.UserDao;
import com.vesna1010.movies.model.Authority;
import com.vesna1010.movies.model.User;

public class UserDaoTest extends BaseDaoTest {

	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void findAllUsersTest() {
		List<User> users = userDao.findAllUsers();

		assertThat(users, hasSize(2));
		assertEquals(users.get(0), userA);
		assertEquals(users.get(1), userB);
	}

	@Test
	public void findUserByUsernameTest() {
		User user = userDao.findUserByUsername("UsernameA");

		assertThat(user.getAuthorities(), hasSize(2));
		assertThat(user.getMovies(), hasSize(1));
	}

	@Test
	public void existsUserByUsernameTest() {
		assertTrue(userDao.existsUserByUsername("UsernameA"));
		assertFalse(userDao.existsUserByUsername("UsernameC"));
	}

	@Test
	public void saveUserTest() {
		User user = new User("UsernameC", passwordEncoder.encode("PasswordC"), true, false,
				new HashSet<Authority>(Arrays.asList(authorityA)));

		userDao.saveUser(user);

		assertTrue(userDao.existsUserByUsername("UsernameC"));
	}

	@Test
	public void updateUserTest() {
		User user = userDao.findUserByUsername("UsernameA");

		user.setAuthorities(new HashSet<Authority>(Arrays.asList(authorityA)));

		userDao.updateUser(user);

		user = userDao.findUserByUsername("UsernameA");

		assertThat(user.getAuthorities(), hasSize(1));
		assertThat(user.getMovies(), hasSize(1));
	}

	@Test
	public void deleteUserTest() {
		User user = userDao.findUserByUsername("UsernameA");

		userDao.deleteUser(user);

		assertFalse(userDao.existsUserByUsername("UsernameA"));
	}

}
