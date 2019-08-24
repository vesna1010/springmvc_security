package com.vesna1010.movies.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.vesna1010.movies.dao.AuthorityDao;
import com.vesna1010.movies.model.Authority;

@Repository("authorityDao")
public class AuthorityDaoImpl extends DaoImpl implements AuthorityDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Authority> findAllAuthorities() {
		Criteria criteria = session().createCriteria(Authority.class);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("id"));

		return (List<Authority>) criteria.list();
	}

	@Override
	public Authority findAuthorityById(int id) {
		Criteria criteria = session().createCriteria(Authority.class);

		criteria.add(Restrictions.eq("id", id));

		return (Authority) criteria.uniqueResult();
	}

}
