package ru.melnikov.computershop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.melnikov.computershop.exception.LaptopNotFoundException;
import ru.melnikov.computershop.repository.LaptopRepository;
import ru.melnikov.computershop.repository.ProductRepository;
import ru.melnikov.computershop.service.product.LaptopService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.melnikov.computershop.util.TestConstants.TEST_LAPTOP;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOG;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOGLOCK;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_LAPTOP;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRODUCT_DATA;

@SpringBootTest
public class LaptopServiceTest {
    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LaptopService laptopService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void removeTestData() {
        jdbcTemplate.execute(TRUNCATE_LAPTOP);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @AfterEach
    void dropLiquibaseChangelogAndRemoveTestData() {
        jdbcTemplate.execute(TRUNCATE_LAPTOP);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
        jdbcTemplate.execute(TRUNCATE_CHANGELOG);
        jdbcTemplate.execute(TRUNCATE_CHANGELOGLOCK);
    }

    @Test
    public void saveTest() {
        // Given
        var testLaptop = TEST_LAPTOP;
        // When
        var result = laptopService.create(testLaptop);

        // Then
        assertEquals(1, productRepository.findAll().size());
        assertEquals(1, laptopRepository.findAll().size());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(testLaptop);
    }

    @Test
    public void getAllTest() {
        // Given
        var testLaptop = laptopRepository.save(TEST_LAPTOP);
        // When
        var result = laptopService.getAll();
        // Then
        assertEquals(1, result.size());

        assertThat(result.stream().findFirst().get())
                .usingRecursiveComparison()
                .isEqualTo(testLaptop);
    }

    @Test
    public void getByIdTest(){
        // Given
        var testLaptop = laptopRepository.save(TEST_LAPTOP);
        // When
        var result = laptopService.getById(testLaptop.getId());
        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(testLaptop);
    }

    @Test
    public void deleteByIdTest(){
        // Given
        var testLaptop = laptopRepository.save(TEST_LAPTOP);
        // When
        laptopService.deleteById(testLaptop.getId());
        // Then
        assertTrue(laptopRepository.findAll().isEmpty());

        assertThrows(LaptopNotFoundException.class, () -> laptopService.getById(testLaptop.getId()));
    }
}
