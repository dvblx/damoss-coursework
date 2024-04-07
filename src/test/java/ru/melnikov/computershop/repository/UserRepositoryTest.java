package ru.melnikov.computershop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.melnikov.computershop.util.TestConstants.TEST_USER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOG;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOGLOCK;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_LAPTOP;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PERSONAL_COMPUTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRINTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRODUCT_DATA;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_USER;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void removeTestData() {
        jdbcTemplate.execute(TRUNCATE_USER);
        jdbcTemplate.execute(TRUNCATE_PERSONAL_COMPUTER);
        jdbcTemplate.execute(TRUNCATE_LAPTOP);
        jdbcTemplate.execute(TRUNCATE_PRINTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @AfterEach
    void dropLiquibaseChangelogAndRemoveTestData() {
        jdbcTemplate.execute(TRUNCATE_USER);
        jdbcTemplate.execute(TRUNCATE_CHANGELOG);
        jdbcTemplate.execute(TRUNCATE_CHANGELOGLOCK);
    }

    @Test
    void findByUsername(){
        // Given
        var testUser = TEST_USER;
        var testUsername = "findIt";
        testUser.setUsername(testUsername);
        userRepository.save(testUser);

        // When
        var searchResult = userRepository.findByUsername(testUsername);

        // Then
        assertTrue(searchResult.isPresent());

        assertThat(searchResult.get())
                .usingRecursiveComparison()
                .isEqualTo(testUser);

    }

    @Test
    void existsByUsername(){
        // Given
        var testUser = TEST_USER;
        var testUsername = "findIt";
        testUser.setUsername(testUsername);
        userRepository.save(testUser);

        // When
        var queryPositiveResult = userRepository.existsByUsername(testUsername);

        var queryNegativeResult = userRepository.existsByUsername("123");

        // Then
        assertTrue(queryPositiveResult);

        assertFalse(queryNegativeResult);
    }

    @Test
    void existsByEmail(){
        // Given
        var testUser = TEST_USER;
        var testEmail = "foo@bar.com";
        testUser.setEmail(testEmail);
        userRepository.save(testUser);

        // When
        var queryPositiveResult = userRepository.existsByEmail(testEmail);

        var queryNegativeResult = userRepository.existsByEmail("123@abc.com");

        // Then
        assertTrue(queryPositiveResult);

        assertFalse(queryNegativeResult);
    }
}
