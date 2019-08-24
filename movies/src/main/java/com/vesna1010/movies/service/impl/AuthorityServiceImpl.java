package com.vesna1010.movies.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vesna1010.movies.dao.AuthorityDao;
import com.vesna1010.movies.model.Authority;
import com.vesna1010.movies.service.AuthorityService;

@Transactional
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityDao authorityDao;

	@Override
	public List<Authority> findAllAuthorities() {
		return authorityDao.findAllAuthorities();
	}

	@Override
	public Authority findAuthorityById(int id) {
		return authorityDao.findAuthorityById(id);
	}

}
