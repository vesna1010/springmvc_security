package com.vesna1010.movies.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
import com.vesna1010.movies.dao.MovieDao;
import com.vesna1010.movies.dao.UserDao;
import com.vesna1010.movies.model.Movie;
import com.vesna1010.movies.service.MovieService;
import com.vesna1010.movies.service.impl.MovieServiceImpl;

public class MovieServiceTest extends BaseServiceTest {

	@InjectMocks
	private MovieService movieService = new MovieServiceImpl();
	@Mock
	private MovieDao movieDao;
	@Mock
	private UserDao userDao;

	@Test
	public void findAllMoviesTest() {
		when(movieDao.findAllMovies()).thenReturn(Arrays.asList(movieA, movieB));

		List<Movie> movies = movieService.findAllMovies();

		assertThat(movies, hasSize(2));
		verify(movieDao, times(1)).findAllMovies();
	}

	@Test
	public void findMovieByIdTest() {
		when(movieDao.findMovieById(1)).thenReturn(movieA);

		Movie movie = movieService.findMovieById(1);

		assertThat(movie.getTitle(), equalTo("Title A"));
		verify(movieDao, times(1)).findMovieById(1L);
	}

	@Test
	public void saveOrUpdateMovieTest() {
		doNothing().when(movieDao).saveOrUpdateMovie(movieA);

		movieService.saveOrUpdateMovie(movieA);

		verify(movieDao, times(1)).saveOrUpdateMovie(movieA);
	}

	@Test
	public void deleteMovieTest() {
		doNothing().when(movieDao).deleteMovie(movieA);

		movieService.deleteMovie(movieA);

		verify(movieDao, times(1)).deleteMovie(movieA);
	}

	@Test
	public void saveUserVoteTest() {
		doNothing().when(movieDao).saveOrUpdateMovie(movieA);
		doNothing().when(userDao).updateUser(userB);

		movieService.saveUserVote(movieA, userB);

		assertTrue(movieA.getVotes() == 2);
		assertTrue(userB.isVote());
		verify(movieDao, times(1)).saveOrUpdateMovie(movieA);
		verify(userDao, times(1)).updateUser(userB);
	}

}
