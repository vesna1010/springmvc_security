package com.vesna1010.movies.test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import com.vesna1010.movies.enums.Genre;
import com.vesna1010.movies.model.Authority;
import com.vesna1010.movies.model.Movie;
import com.vesna1010.movies.model.User;

public abstract class BaseTest {

	protected final Authority authorityA = new Authority(1, "USER");
	protected final Authority authorityB = new Authority(2, "ADMIN");

	protected final User userA = new User("UsernameA", "PasswordA", true, false,
			new HashSet<Authority>(Arrays.asList(authorityA, authorityB)));
	protected final User userB = new User("UsernameB", "PasswordB", true, false,
			new HashSet<Authority>(Arrays.asList(authorityA)));

	protected final Movie movieA = new Movie(1, 1, "Title A", Genre.ACTION, "2017", "Actors A",
			"https://www.imdb.com/title/tt0497465/?ref_=nv_sr_1",
			new GregorianCalendar(2017, Calendar.MAY, 20).getTime(), userA);
	protected final Movie movieB = new Movie(2, 0, "Title B", Genre.ACTION, "2017", "Actors B",
			"https://www.imdb.com/title/tt7233640/?ref_=fn_al_tt_4",
			new GregorianCalendar(2017, Calendar.MAY, 21).getTime(), userB);

}
