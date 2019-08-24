package com.vesna1010.movies.test.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import com.vesna1010.movies.controller.UserController;
import com.vesna1010.movies.converter.AuthorityConverter;
import com.vesna1010.movies.converter.UserConverter;
import com.vesna1010.movies.model.User;
import com.vesna1010.movies.service.AuthorityService;
import com.vesna1010.movies.service.UserService;
import com.vesna1010.movies.validator.UserValidator;

public class UserControllerTest extends BaseControllerTest {

	@InjectMocks
	@Autowired
	private UserController userController;
	@InjectMocks
	@Autowired
	private UserConverter userConverter;
	@InjectMocks
	@Autowired
	private AuthorityConverter authorityConverter;
	@InjectMocks
	@Autowired
	private UserValidator userValidator;
	@Mock
	private UserService userService;
	@Mock
	private AuthorityService authorityService;

	@Test
	@WithAnonymousUser
	public void renderUsersPageWithAllUsersWithAnonymousTest() throws Exception {
		when(userService.findAllUsers()).thenReturn(Arrays.asList(userA, userB));

		mockMvc.perform(get("/users"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));

		verify(userService, never()).findAllUsers();
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void renderUsersPageWithAllUsersWithUserTest() throws Exception {
		when(userService.findAllUsers()).thenReturn(Arrays.asList(userA, userB));

		mockMvc.perform(get("/users"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));

		verify(userService, never()).findAllUsers();
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "ADMIN")
	public void renderUsersPageWithAllUsersWithAdminTest() throws Exception {
		when(userService.findAllUsers()).thenReturn(Arrays.asList(userA, userB));

		mockMvc.perform(get("/users"))
		       .andExpect(status().isOk())
	           .andExpect(model().attribute("users", hasSize(2)))
	           .andExpect(view().name("users"));

		verify(userService, times(1)).findAllUsers();
	}
	
	@Test
	@WithAnonymousUser
	public void renderEmptyUserFormWithAnonymousUserTest() throws Exception {
		when(authorityService.findAllAuthorities()).thenReturn(Arrays.asList(authorityA, authorityB));
		
		mockMvc.perform(get("/userForm"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
		
		verify(authorityService, never()).findAllAuthorities();
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void renderEmptyUserFormWithUserTest() throws Exception {
		when(authorityService.findAllAuthorities()).thenReturn(Arrays.asList(authorityA, authorityB));
		
		mockMvc.perform(get("/userForm"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
		
		verify(authorityService, never()).findAllAuthorities();
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "ADMIN")
	public void renderEmptyUserFormWithAdminTest() throws Exception {
		when(authorityService.findAllAuthorities()).thenReturn(Arrays.asList(authorityA, authorityB));
		
		mockMvc.perform(get("/userForm"))
		       .andExpect(status().isOk())
	           .andExpect(model().attribute("user", equalTo(new User())))
	           .andExpect(model().attribute("authorities", hasSize(2)))
	           .andExpect(view().name("userForm"));
		
		verify(authorityService, times(1)).findAllAuthorities();
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "ADMIN")
	public void saveUserAndRenderUserFormValidFormTest() throws Exception {
		when(userService.existsUserByUsername("UsernameB")).thenReturn(false);
		when(authorityService.findAuthorityById(1)).thenReturn(authorityA);
		doNothing().when(userService).saveUser(userB);
		
		mockMvc.perform(
				post("/saveUser")
				.param("username", "UsernameB")
				.param("password", "PasswordB")
				.param("enabled", "true")
				.param("isVote", "false")
				.param("authorities", "1")
				.with(csrf())
				)
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/userForm"));
		
		verify(userService, times(1)).existsUserByUsername("UsernameB");
		verify(authorityService, times(1)).findAuthorityById(1);
		verify(userService, times(1)).saveUser(userB);
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "ADMIN")
	public void saveUserAndRenderUserFormInvalidFormTest() throws Exception {
		when(authorityService.findAllAuthorities()).thenReturn(Arrays.asList(authorityA, authorityB));
		when(userService.existsUserByUsername("UsernameB")).thenReturn(true);
		when(authorityService.findAuthorityById(1)).thenReturn(authorityA);
		doNothing().when(userService).saveUser(userB);
		
		mockMvc.perform(
				post("/saveUser")
				.param("username", "UsernameB")
				.param("password", "PasswordB")
				.param("enabled", "true")
				.param("isVote", "false")
				.param("authorities", "1")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("user", "username"))
		       .andExpect(model().attribute("user", userB))
		       .andExpect(model().attribute("authorities", hasSize(2)))
		       .andExpect(view().name("userForm"));
		
		verify(authorityService, times(1)).findAllAuthorities();
		verify(userService, times(1)).existsUserByUsername("UsernameB");
		verify(authorityService, times(1)).findAuthorityById(1);
		verify(userService, times(0)).saveUser(userB);
	}
	
	@Test
	@WithAnonymousUser
	public void resetUserVotesAndRedirectToUsersPageWithAnonymousUserTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).resetUserVotes(userA);
		
		mockMvc.perform(get("/resetVote/UsernameA"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
		
		verify(userService, never()).findUserByUsername("UsernameA");
		verify(userService, never()).resetUserVotes(userA);
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void resetUserVotesAndRedirectToUsersPageWithUserTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).resetUserVotes(userA);
		
		mockMvc.perform(get("/resetVote/UsernameA"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
		
		verify(userService, never()).findUserByUsername("UsernameA");
		verify(userService, never()).resetUserVotes(userA);
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "ADMIN")
	public void resetUserVotesAndRedirectToUsersPageWithAdminTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).resetUserVotes(userA);
		
		mockMvc.perform(get("/resetVote/UsernameA"))
		       .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrl("/users"));
		
		verify(userService, times(1)).findUserByUsername("UsernameA");
		verify(userService, times(1)).resetUserVotes(userA);
	}
	
	@Test
	@WithAnonymousUser
	public void disableUserAndRedirectToUsersPageWithAnonymousUserTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).disableUser(userA);
		
		mockMvc.perform(get("/disableUser/UsernameA"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
		
		verify(userService, never()).findUserByUsername("UsernameA");
		verify(userService, never()).disableUser(userA);
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void disableUserAndRedirectToUsersPageWithUserTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).disableUser(userA);
		
		mockMvc.perform(get("/disableUser/UsernameA"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
		
		verify(userService, never()).findUserByUsername("UsernameA");
		verify(userService, never()).disableUser(userA);
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "ADMIN")
	public void disableUserAndRedirectToUsersPageWithAdminTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).disableUser(userA);
		
		mockMvc.perform(get("/disableUser/UsernameA"))
		       .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrl("/users"));
		
		verify(userService, times(1)).findUserByUsername("UsernameA");
		verify(userService, times(1)).disableUser(userA);
	}
	
	@Test
	@WithAnonymousUser
	public void deleteUserAndRedirectToUsersPageWithAnonymousUserTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).deleteUser(userA);
		
		mockMvc.perform(get("/deleteUser/UsernameA"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
		
		verify(userService, never()).findUserByUsername("UsernameA");
		verify(userService, never()).deleteUser(userA);
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void deleteUserAndRedirectToUsersPageWithUserTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).deleteUser(userA);
		
		mockMvc.perform(get("/deleteUser/UsernameA"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
		
		verify(userService, never()).findUserByUsername("UsernameA");
		verify(userService, never()).deleteUser(userA);
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "ADMIN")
	public void deleteUserAndRedirectToUsersPageWithAdminTest() throws Exception {
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(userService).deleteUser(userA);
		
		mockMvc.perform(get("/deleteUser/UsernameA"))
		       .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrl("/users"));
		
		verify(userService, times(1)).findUserByUsername("UsernameA");
		verify(userService, times(1)).deleteUser(userA);
	}
	
	@Test
	@WithAnonymousUser
	public void findLoggedUsersWithAnonymousUserTest() throws Exception {
		when(userService.findLoggedUsers()).thenReturn(Arrays.asList(userA, userB));

		mockMvc.perform(get("/loggedUsers"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));

		verify(userService, never()).findLoggedUsers();
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void findLoggedUsersWithUserTest() throws Exception {
		when(userService.findLoggedUsers()).thenReturn(Arrays.asList(userA, userB));

		mockMvc.perform(get("/loggedUsers"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));

		verify(userService, never()).findLoggedUsers();
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "ADMIN")
	public void findLoggedUsersWithAdminTest() throws Exception {
		when(userService.findLoggedUsers()).thenReturn(Arrays.asList(userA, userB));

		mockMvc.perform(get("/loggedUsers"))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$", hasSize(2)))
	           .andExpect(jsonPath("$[0].username", equalTo("UsernameA")))
	           .andExpect(jsonPath("$[1].username", equalTo("UsernameB")))
	           .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		verify(userService, times(1)).findLoggedUsers();
	}
	
}
