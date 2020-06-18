package com.vesna1010.movies.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.vesna1010.movies.model.Authority;
import com.vesna1010.movies.model.User;
import com.vesna1010.movies.service.AuthorityService;
import com.vesna1010.movies.service.UserService;
import com.vesna1010.movies.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String renderUsersPageWithAllUsers(Model model) {
		model.addAttribute("users", userService.findAllUsers());

		return "users";
	}

	@RequestMapping(value = "/userForm", method = RequestMethod.GET)
	public String renderEmptyUserForm(Model model) {
		model.addAttribute("user", new User());

		return "userForm";
	}

	@ModelAttribute("authorities")
	public List<Authority> authorities() {
		return authorityService.findAllAuthorities();
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUserAndRenderUserForm(User user, BindingResult result) {
		userValidator.validate(user, result);

		if (result.hasErrors()) {
			return "userForm";
		}

		userService.saveUser(user);

		return "redirect:/userForm";
	}

	@RequestMapping(value = "/resetVote/{username}", method = RequestMethod.GET)
	public String resetUserVotesAndRedirectToUsersPage(@PathVariable("username") User user) {
		userService.resetUserVotes(user);

		return "redirect:/users";
	}

	@RequestMapping(value = "/disableUser/{username}", method = RequestMethod.GET)
	public String disableUserAndRedirectToUsersPage(@PathVariable("username") User user) {
		userService.disableUser(user);

		return "redirect:/users";
	}

	@RequestMapping(value = "/deleteUser/{username}", method = RequestMethod.GET)
	public String deleteUserAndRedirectToUsersPage(@PathVariable("username") User user) {
		userService.deleteUser(user);

		return "redirect:/users";
	}

	@RequestMapping(value = "/loggedUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody List<User> findLoggedUsers() {
		return userService.findLoggedUsers();
	}

}
