package com.vesna1010.movies.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.vesna1010.movies.dao.AuthorityDao;
import com.vesna1010.movies.model.Authority;
import com.vesna1010.movies.service.AuthorityService;
import com.vesna1010.movies.service.impl.AuthorityServiceImpl;

public class AuthorityServiceTest extends BaseServiceTest {

	@InjectMocks
	private AuthorityService authorityService = new AuthorityServiceImpl();
	@Mock
	private AuthorityDao authorityDao;

	@Test
	public void findAllAuthoritiesTest() {
		when(authorityDao.findAllAuthorities()).thenReturn(Arrays.asList(authorityA, authorityB));

		List<Authority> authorities = authorityService.findAllAuthorities();

		assertThat(authorities, hasSize(2));
		verify(authorityDao, times(1)).findAllAuthorities();
	}

	@Test
	public void findAuthorityByIdTest() {
		when(authorityDao.findAuthorityById(1)).thenReturn(authorityA);

		Authority authority = authorityService.findAuthorityById(1);

		assertThat(authority.getName(), equalTo("USER"));
		verify(authorityDao, times(1)).findAuthorityById(1);
	}
}
