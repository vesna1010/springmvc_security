package com.vesna1010.movies.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import com.vesna1010.movies.enums.Genre;

@Entity
@Table(name = "MOVIES")
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "VOTES")
	private long votes;

	@Pattern(regexp = "^([\\w\\:\\-]+\\s?){3,}$")
	@Column(name = "TITLE")
	private String title;

	@NotNull(message = "{movie.genre}")
	@Enumerated(EnumType.STRING)
	@Column(name = "GENRE")
	private Genre genre;

	@Pattern(regexp = "^[\\d]{4}$")
	@Column(name = "YEAR")
	private String year;

	@Pattern(regexp = "^(([a-zA-Z\\,]+\\s?)+){5,}$")
	@Column(name = "ACTORS")
	private String actors;

	@Pattern(regexp = "^(https:\\/\\/)?(www.imdb.com\\/title\\/tt)\\d{7}(\\/\\?ref_=)[\\w]{5,}$")
	@Column(name = "URL")
	private String url;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Column(name = "DATE")
	private Date date = new Date();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User user;

	public Movie() {
	}

	public Movie(long votes, String title, Genre genre, String year, String actors, String url, Date date, User user) {
		this(0, votes, title, genre, year, actors, url, date, user);
	}

	public Movie(long id, long votes, String title, Genre genre, String year, String actors, String url, Date date,
			User user) {
		this.id = id;
		this.votes = votes;
		this.title = title;
		this.genre = genre;
		this.year = year;
		this.actors = actors;
		this.url = url;
		this.date = date;
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getVotes() {
		return votes;
	}

	public void setVotes(long votes) {
		this.votes = votes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
