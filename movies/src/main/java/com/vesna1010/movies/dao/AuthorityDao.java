package com.vesna1010.movies.dao;

import java.util.List;
import com.vesna1010.movies.model.Authority;

public interface AuthorityDao {

	List<Authority> findAllAuthorities();

	Authority findAuthorityById(int id);

}
