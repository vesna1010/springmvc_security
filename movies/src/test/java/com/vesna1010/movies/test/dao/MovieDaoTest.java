package com.vesna1010.movies.test.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.vesna1010.movies.dao.MovieDao;
import com.vesna1010.movies.enums.Genre;
import com.vesna1010.movies.model.Movie;

public class MovieDaoTest extends BaseDaoTest {

	@Autowired
	private MovieDao movieDao;

	@Test
	public void findAllMoviesTest() {
		List<Movie> movies = movieDao.findAllMovies();

		assertThat(movies, hasSize(2));
		assertEquals(movies.get(0), movieA);
		assertEquals(movies.get(1), movieB);
	}

	@Test
	public void findMovieByIdTest() {
		Movie movie = movieDao.findMovieById(1);

		assertThat(movie.getTitle(), equalTo("Title A"));
		assertThat(movie.getUser(), equalTo(userA));
	}

	@Test
	public void saveOrUpdateMovieTest() {
		Movie movie = new Movie(0, "Title C", Genre.ACTION, "2018", "Actors C",
				"https://www.imdb.com/title/tt0497465/?ref_=nv_sr_1",
				new GregorianCalendar(2018, Calendar.OCTOBER, 20).getTime(), userA);

		movieDao.saveOrUpdateMovie(movie);

		assertNotNull(movie.getId());
	}

	@Test
	public void deleteMovieTest() {
		Movie movie = movieDao.findMovieById(1);

		movieDao.deleteMovie(movie);

		assertNull(movieDao.findMovieById(1));
	}

}
