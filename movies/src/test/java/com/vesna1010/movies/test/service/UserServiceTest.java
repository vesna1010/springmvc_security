package com.vesna1010.movies.test.service;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vesna1010.movies.dao.UserDao;
import com.vesna1010.movies.model.User;
import com.vesna1010.movies.service.UserService;
import com.vesna1010.movies.service.impl.UserServiceImpl;

public class UserServiceTest extends BaseServiceTest {

	@InjectMocks
	private UserService userService = new UserServiceImpl();
	@Mock
	private UserDao userDao;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private SessionRegistry sessionRegistry;

	@Test
	public void findAllUsersTest() {
		when(userDao.findAllUsers()).thenReturn(Arrays.asList(userA, userB));

		List<User> users = userService.findAllUsers();

		assertThat(users, hasSize(2));
		verify(userDao, times(1)).findAllUsers();
	}

	@Test
	public void findLoggedUsersTest() {
		when(sessionRegistry.getAllPrincipals()).thenReturn(Arrays.asList(userA, userB));
		when(userDao.findUserByUsername("UsernameA")).thenReturn(userA);
		when(userDao.findUserByUsername("UsernameB")).thenReturn(userB);

		List<User> users = userService.findLoggedUsers();

		assertThat(users, hasSize(0));
		verify(sessionRegistry, times(1)).getAllPrincipals();
		verify(userDao, times(0)).findUserByUsername("UsernameA");
		verify(userDao, times(0)).findUserByUsername("UsernameB");
	}

	@Test
	public void findUserByUsernameTest() {
		when(userDao.findUserByUsername("UsernameA")).thenReturn(userA);

		User user = userService.findUserByUsername("UsernameA");

		assertThat(user.getAuthorities(), hasSize(2));
		verify(userDao, times(1)).findUserByUsername("UsernameA");
	}

	@Test
	public void isUserExistsByUsernameTest() {
		when(userDao.existsUserByUsername("UsernameA")).thenReturn(true);

		boolean isExist = userService.existsUserByUsername("UsernameA");

		assertTrue(isExist);
		verify(userDao, times(1)).existsUserByUsername("UsernameA");
	}

	@Test
	public void saveUserTest() {
		doNothing().when(userDao).saveUser(userA);

		userService.saveUser(userA);

		verify(userDao, times(1)).saveUser(userA);
		verify(passwordEncoder, times(1)).encode("PasswordA");
	}

	@Test
	public void resetUserVotesTest() {
		doNothing().when(userDao).updateUser(userA);

		userService.resetUserVotes(userA);

		assertFalse(userA.isVote());
		verify(userDao, times(1)).updateUser(userA);
	}

	@Test
	public void deleteUserTest() {
		doNothing().when(userDao).deleteUser(userA);

		userService.deleteUser(userA);

		verify(userDao, times(1)).deleteUser(userA);
	}

	@Test
	public void disableUserTest() {
		doNothing().when(userDao).updateUser(userA);

		userService.disableUser(userA);

		assertFalse(userA.isEnabled());
		verify(userDao, times(1)).updateUser(userA);
	}

}
