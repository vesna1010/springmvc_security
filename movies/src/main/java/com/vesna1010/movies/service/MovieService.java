package com.vesna1010.movies.service;

import java.util.List;
import com.vesna1010.movies.model.Movie;
import com.vesna1010.movies.model.User;

public interface MovieService {

	List<Movie> findAllMovies();

	Movie findMovieById(long id);

	void saveOrUpdateMovie(Movie movie);

	void deleteMovie(Movie movie);

	void saveUserVote(Movie movie, User user);

}
