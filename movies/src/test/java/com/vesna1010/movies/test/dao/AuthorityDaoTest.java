package com.vesna1010.movies.test.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.vesna1010.movies.dao.AuthorityDao;
import com.vesna1010.movies.model.Authority;

public class AuthorityDaoTest extends BaseDaoTest {

	@Autowired
	private AuthorityDao authorityDao;

	@Test
	public void findAllAuthoritiesTest() {
		List<Authority> authorities = authorityDao.findAllAuthorities();

		assertThat(authorities, hasSize(2));
		assertEquals(authorities.get(0), authorityA);
		assertEquals(authorities.get(1), authorityB);
	}

	@Test
	public void findAuthorityByIdTest() {
		Authority authority = authorityDao.findAuthorityById(1);

		assertThat(authority.getName(), equalTo("USER"));
		assertThat(authority.getUsers(), hasSize(2));
	}

}
