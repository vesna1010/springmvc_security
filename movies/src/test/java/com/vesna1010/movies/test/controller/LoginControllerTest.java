package com.vesna1010.movies.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

public class LoginControllerTest extends BaseControllerTest {

	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void authenticatedUserCannotGetLoginPage() throws Exception {
		mockMvc.perform(get("/login", "/login/"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}

	@Test
	@WithAnonymousUser
	public void anonymousUserGetLoginPage() throws Exception {
		mockMvc.perform(get("/login", "/login/"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("loginForm"));
	}

	@Test
	@WithAnonymousUser
	public void anonymousUserGetDeniedPage() throws Exception {
		mockMvc.perform(get("/denied", "/denied/"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("denied"));
	}

}
