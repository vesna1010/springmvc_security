package com.vesna1010.movies.test.dao;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.vesna1010.movies.test.BaseTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/spring-database.xml")
@Transactional
@Commit
@Sql(scripts = "classpath:sql/init.sql")
public abstract class BaseDaoTest extends BaseTest {

}
