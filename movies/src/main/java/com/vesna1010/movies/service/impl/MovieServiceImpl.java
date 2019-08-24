package com.vesna1010.movies.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vesna1010.movies.dao.MovieDao;
import com.vesna1010.movies.dao.UserDao;
import com.vesna1010.movies.model.Movie;
import com.vesna1010.movies.model.User;
import com.vesna1010.movies.service.MovieService;

@Transactional
@Service("movieService")
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieDao movieDao;
	@Autowired
	private UserDao userDao;

	@Override
	public List<Movie> findAllMovies() {
		return movieDao.findAllMovies();
	}

	@Override
	public Movie findMovieById(long id) {
		return movieDao.findMovieById(id);
	}

	@Override
	public void saveOrUpdateMovie(Movie movie) {
		movieDao.saveOrUpdateMovie(movie);
	}

	@Override
	public void deleteMovie(Movie movie) {
		movieDao.deleteMovie(movie);
	}

	@Override
	public void saveUserVote(Movie movie, User user) {
		user.setVote(true);
		movie.setVotes(movie.getVotes() + 1);

		userDao.updateUser(user);
		movieDao.saveOrUpdateMovie(movie);
	}

}
