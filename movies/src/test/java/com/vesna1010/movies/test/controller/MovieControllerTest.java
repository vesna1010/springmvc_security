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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import com.vesna1010.movies.controller.MovieController;
import com.vesna1010.movies.converter.MovieConverter;
import com.vesna1010.movies.converter.UserConverter;
import com.vesna1010.movies.enums.Genre;
import com.vesna1010.movies.model.Movie;
import com.vesna1010.movies.service.MovieService;
import com.vesna1010.movies.service.UserService;

public class MovieControllerTest extends BaseControllerTest {

	@InjectMocks
	@Autowired
	private MovieController movieController;
	@InjectMocks
	@Autowired
	private MovieConverter movieConverter;
	@InjectMocks
	@Autowired
	private UserConverter userConverter;
	@Mock
	private UserService userService;
	@Mock
	private MovieService movieService;
	
	@Test
	@WithAnonymousUser
	public void renderMoviesPageWithMoviesWithAnonymousTest() throws Exception {
		when(movieService.findAllMovies()).thenReturn(Arrays.asList(movieA, movieB));

		mockMvc.perform(get("/"))
		       .andExpect(status().isOk())
               .andExpect(model().attribute("movies", hasSize(2)))
               .andExpect(view().name("movies"));

		verify(movieService, times(1)).findAllMovies();
	}
	
	@Test
	@WithAnonymousUser
	public void renderEmptyMovieFormWithAnonymousTest() throws Exception {
		mockMvc.perform(get("/movieForm"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void renderEmptyMovieFormWithUserTest() throws Exception {
		mockMvc.perform(get("/movieForm"))
		       .andExpect(status().isOk())
               .andExpect(model().attribute("movie", equalTo(new Movie())))
               .andExpect(view().name("movieForm"));
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void saveMovieAndRenderMovieFormValidFormTest() throws Exception {
		Movie movie = new Movie(0, "Title", Genre.ACTION, "2018", "Actors",
				"https://www.imdb.com/title/tt0497465/?ref_=nv_sr_1",
				new GregorianCalendar(2017, Calendar.JULY, 7).getTime(), userA);
	
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(movieService).saveOrUpdateMovie(movie);
		
		mockMvc.perform(
				post("/saveMovie")
				.param("votes", "0")
				.param("title", "Title")
				.param("genre", "ACTION")
				.param("year", "2018")
				.param("actors", "Actors")
				.param("url", "https://www.imdb.com/title/tt0497465/?ref_=nv_sr_1")
				.param("date", "07.07.2017")
				.param("user", "UsernameA")
				.with(csrf())
				)
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/movieForm"));
		
		verify(userService, times(1)).findUserByUsername("UsernameA");
		verify(movieService, times(1)).saveOrUpdateMovie(movie);
	}
	
	@Test
	@WithMockUser(username = "Username", authorities = "USER")
	public void saveMovieAndRenderMovieFormInvalidFormTest() throws Exception {
		Movie movie = new Movie(0, "Title", Genre.ACTION, "2018", "Actors",
				"invalidUrl", new GregorianCalendar(2017, Calendar.JULY, 7).getTime(), userA);
		
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(movieService).saveOrUpdateMovie(movie);
		
		mockMvc.perform(
				post("/saveMovie")
				.param("votes", "0")
				.param("title", "Title")
				.param("genre", "ACTION")
				.param("year", "2018")
				.param("actors", "Actors")
				.param("url", "invalidUrl")
				.param("date", "07.07.2017")
				.param("user", "UsernameA")
				.with(csrf())
				)
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("movie", "url"))
		       .andExpect(model().attribute("movie", movie))
		       .andExpect(view().name("movieForm"));
		
		verify(userService, times(1)).findUserByUsername("UsernameA");
		verify(movieService, times(0)).saveOrUpdateMovie(movie);
	}
	
	@Test
	@WithAnonymousUser
	public void renderMovieFormWithMovieWithAnonymousTest() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movieA);
		
		mockMvc.perform(get("/editMovie/1"))
		       .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrlPattern("**/login"));
		
		verify(movieService, times(0)).findMovieById(1);
	}
	
	@Test
	@WithMockUser(username = "UsernameA", authorities = "USER")
	public void renderMovieFormWithMovieEnabledTest() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movieA);
	
		mockMvc.perform(get("/editMovie/1"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("movie", movieA))
		       .andExpect(view().name("movieForm"));
		
		verify(movieService, times(2)).findMovieById(1);
	}
	
	@Test
	@WithMockUser(username = "UsernameB", authorities = "USER")
	public void renderMovieFormWithMovieAccessDeniedTest() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movieA);
		
		mockMvc.perform(get("/editMovie/1"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
		
		verify(movieService, times(1)).findMovieById(1);
	}
	
	@Test
	@WithAnonymousUser
	public void deleteMovieAndRedirectToMoviesPageWithAnonymousTest() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movieA);
		doNothing().when(movieService).deleteMovie(movieA);
		
		mockMvc.perform(get("/deleteMovie/1"))
		       .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrlPattern("**/login"));
		
		verify(movieService, times(0)).findMovieById(1);
		verify(movieService, times(0)).deleteMovie(movieA);
	}
	
	@Test
	@WithMockUser(username = "UsernameA", authorities = "USER")
	public void deleteMovieAndRedirectToMoviesPageEnabledTest() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movieA);
		doNothing().when(movieService).deleteMovie(movieA);
		
		mockMvc.perform(get("/deleteMovie/1"))
		       .andExpect(status().is3xxRedirection())
	           .andExpect(redirectedUrl("/"));
		
		verify(movieService, times(2)).findMovieById(1);
		verify(movieService, times(1)).deleteMovie(movieA);
	}
	
	@Test
	@WithMockUser(username = "UsernameB", authorities = "USER")
	public void deleteMovieAndRedirectToMoviesPageAccessDeniedTest() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movieA);
		doNothing().when(movieService).deleteMovie(movieA);
		
		mockMvc.perform(get("/deleteMovie/1"))
		       .andExpect(status().isForbidden())
	           .andExpect(forwardedUrl("/denied"));
		
		verify(movieService, times(1)).findMovieById(1);
		verify(movieService, times(0)).deleteMovie(movieA);
	}	
	
	@Test
	@WithAnonymousUser
	public void saveUserVoteAndRedirectToMoviesPageWithAnonymousTest() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movieA);
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(movieService).saveUserVote(movieA, userA);
		
		mockMvc.perform(get("/saveVote/1"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
		
		verify(movieService, never()).findMovieById(1);
		verify(userService, never()).findUserByUsername("UsernameA");
		verify(movieService, never()).saveUserVote(movieA, userA);
	}
	
	@Test
	@WithMockUser(username = "UsernameA", authorities = "USER")
	public void saveUserVoteAndRedirectToMoviesPageWithUserTest() throws Exception {
		when(movieService.findMovieById(1)).thenReturn(movieA);
		when(userService.findUserByUsername("UsernameA")).thenReturn(userA);
		doNothing().when(movieService).saveUserVote(movieA, userA);
		
		mockMvc.perform(get("/saveVote/1"))
		       .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/"));
		
		verify(movieService, times(1)).findMovieById(1);
		verify(userService, times(1)).findUserByUsername("UsernameA");
		verify(movieService, times(1)).saveUserVote(movieA, userA);
	}
	
}
