package com.vesna1010.movies.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.vesna1010.movies.dao.UserDao;
import com.vesna1010.movies.model.User;

@Repository("userDao")
public class UserDaoImpl extends DaoImpl implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUsers() {
		Criteria criteria = session().createCriteria(User.class);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("username"));

		return (List<User>) criteria.list();
	}

	@Override
	public boolean existsUserByUsername(String username) {
		return (findUserByUsername(username) != null);
	}

	@Override
	public User findUserByUsername(String username) {
		Criteria criteria = session().createCriteria(User.class);

		criteria.add(Restrictions.eq("username", username));

		return (User) criteria.uniqueResult();
	}

	@Override
	public void saveUser(User user) {
		session().save(user);
	}

	@Override
	public void updateUser(User user) {
		session().update(user);
	}

	@Override
	public void deleteUser(User user) {
		session().delete(user);
	}

}
