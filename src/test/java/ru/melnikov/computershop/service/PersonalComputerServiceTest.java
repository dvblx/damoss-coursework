package ru.melnikov.computershop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.melnikov.computershop.exception.PersonalComputerNotFoundException;
import ru.melnikov.computershop.repository.PersonalComputerRepository;
import ru.melnikov.computershop.repository.ProductRepository;
import ru.melnikov.computershop.service.product.PersonalComputerService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.melnikov.computershop.util.TestConstants.TEST_PERSONAL_COMPUTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOG;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOGLOCK;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PERSONAL_COMPUTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRODUCT_DATA;

@SpringBootTest
public class PersonalComputerServiceTest {
    @Autowired
    private PersonalComputerService computerService;
    @Autowired
    private PersonalComputerRepository computerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void removeData(){
        jdbcTemplate.execute(TRUNCATE_PERSONAL_COMPUTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @AfterEach
    void dropLiquibaseChangelogAndRemoveTestData() {
        jdbcTemplate.execute(TRUNCATE_PERSONAL_COMPUTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
        jdbcTemplate.execute(TRUNCATE_CHANGELOG);
        jdbcTemplate.execute(TRUNCATE_CHANGELOGLOCK);
    }

    @Test
    public void saveTest() {
        // Given
        var testComputer = TEST_PERSONAL_COMPUTER;
        // When
        var result = computerService.create(testComputer);

        // Then
        assertEquals(1, productRepository.findAll().size());
        assertEquals(1, computerRepository.findAll().size());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(testComputer);
    }

    @Test
    public void getAllTest() {
        // Given
        var testComputer = computerRepository.save(TEST_PERSONAL_COMPUTER);
        // When
        var result = computerService.getAll();
        // Then
        assertEquals(1, result.size());

        assertThat(result.stream().findFirst().get())
                .usingRecursiveComparison()
                .isEqualTo(testComputer);
    }

    @Test
    public void getByIdTest(){
        // Given
        var testComputer = computerRepository.save(TEST_PERSONAL_COMPUTER);
        // When
        var result = computerService.getById(testComputer.getId());
        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(testComputer);
    }

    @Test
    public void deleteByIdTest(){
        // Given
        var testComputer = computerRepository.save(TEST_PERSONAL_COMPUTER);
        // When
        computerService.deleteById(testComputer.getId());
        // Then
        assertTrue(computerRepository.findAll().isEmpty());

        assertThrows(PersonalComputerNotFoundException.class, () -> computerService.getById(testComputer.getId()));
    }
}
