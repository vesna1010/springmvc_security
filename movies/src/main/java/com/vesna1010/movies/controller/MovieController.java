package com.vesna1010.movies.controller;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.vesna1010.movies.model.Movie;
import com.vesna1010.movies.model.User;
import com.vesna1010.movies.service.MovieService;
import com.vesna1010.movies.service.UserService;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String renderMoviesPageWithMovies(Model model) {
		model.addAttribute("movies", movieService.findAllMovies());

		return "movies";
	}

	@RequestMapping(value = "/movieForm", method = RequestMethod.GET)
	public String renderEmptyMovieForm(Model model) {
		model.addAttribute("movie", new Movie());

		return "movieForm";
	}

	@RequestMapping(value = "/saveMovie", method = RequestMethod.POST)
	public String saveMovieAndRenderMovieForm(@Valid Movie movie, BindingResult result) {
		if (result.hasErrors()) {
			return "movieForm";
		}

		movieService.saveOrUpdateMovie(movie);

		return "redirect:/movieForm";
	}

	@RequestMapping(value = "/editMovie/{id}", method = RequestMethod.GET)
	public String renderMovieFormWithMovie(@PathVariable("id") Movie movie, Model model) {
		model.addAttribute("movie", movie);

		return "movieForm";
	}

	@RequestMapping(value = "/deleteMovie/{id}", method = RequestMethod.GET)
	public String deleteMovieAndRedirectToMoviesPage(@PathVariable("id") Movie movie) {
		movieService.deleteMovie(movie);

		return "redirect:/";
	}

	@RequestMapping("/saveVote/{id}")
	public String saveUserVoteAndRedirectToMoviesPage(@PathVariable("id") Movie movie, Principal principal) {
		User user = userService.findUserByUsername(principal.getName());

		if (!user.isVote()) {
			movieService.saveUserVote(movie, user);
		}

		return "redirect:/";
	}

}
