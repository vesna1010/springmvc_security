package com.vesna1010.movies.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import com.vesna1010.movies.model.Authority;
import com.vesna1010.movies.service.AuthorityService;

public class AuthorityConverter implements Converter<String, Authority> {

	@Autowired
	private AuthorityService authorityService;

	@Override
	public Authority convert(String id) {
		return authorityService.findAuthorityById(Integer.parseInt(id));
	}

}
