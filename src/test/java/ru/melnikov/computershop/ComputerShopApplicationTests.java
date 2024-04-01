package ru.melnikov.computershop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOG;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOGLOCK;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_LAPTOP;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PERSONAL_COMPUTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRINTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRODUCT_DATA;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_USER;

@SpringBootTest
class ComputerShopApplicationTests {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	void clearDb(){
		jdbcTemplate.execute(TRUNCATE_CHANGELOG);
		jdbcTemplate.execute(TRUNCATE_CHANGELOGLOCK);
		jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
		jdbcTemplate.execute(TRUNCATE_PERSONAL_COMPUTER);
		jdbcTemplate.execute(TRUNCATE_LAPTOP);
		jdbcTemplate.execute(TRUNCATE_PRINTER);
		jdbcTemplate.execute(TRUNCATE_USER);
	}

}
