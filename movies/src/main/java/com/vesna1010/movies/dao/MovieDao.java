package com.vesna1010.movies.dao;

import java.util.List;
import com.vesna1010.movies.model.Movie;

public interface MovieDao {

	List<Movie> findAllMovies();

	Movie findMovieById(long id);

	void saveOrUpdateMovie(Movie movie);

	void deleteMovie(Movie movie);

}
