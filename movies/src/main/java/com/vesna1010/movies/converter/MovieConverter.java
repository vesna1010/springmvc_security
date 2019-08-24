package com.vesna1010.movies.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.vesna1010.movies.model.Movie;
import com.vesna1010.movies.service.MovieService;

@Component("movieConverter")
public class MovieConverter implements Converter<String, Movie> {

	@Autowired
	private MovieService movieService;

	@Override
	public Movie convert(String id) {
		return movieService.findMovieById(Long.parseLong(id));
	}

}
