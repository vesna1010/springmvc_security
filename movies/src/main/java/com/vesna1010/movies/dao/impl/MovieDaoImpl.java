package com.vesna1010.movies.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.vesna1010.movies.dao.MovieDao;
import com.vesna1010.movies.model.Movie;

@Repository("movieDao")
public class MovieDaoImpl extends DaoImpl implements MovieDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Movie> findAllMovies() {
		Criteria criteria = session().createCriteria(Movie.class);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("votes"));
		criteria.addOrder(Order.desc("id"));

		return (List<Movie>) criteria.list();
	}

	@Override
	public Movie findMovieById(long id) {
		Criteria criteria = session().createCriteria(Movie.class);

		criteria.add(Restrictions.eq("id", id));

		return (Movie) criteria.uniqueResult();
	}

	@Override
	public void saveOrUpdateMovie(Movie movie) {
		session().saveOrUpdate(movie);
	}

	@Override
	public void deleteMovie(Movie movie) {
		session().delete(movie);
	}

}
