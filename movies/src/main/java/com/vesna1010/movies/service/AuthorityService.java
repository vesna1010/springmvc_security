package com.vesna1010.movies.service;

import java.util.List;
import com.vesna1010.movies.model.Authority;

public interface AuthorityService {

	List<Authority> findAllAuthorities();

	Authority findAuthorityById(int id);
	
}
