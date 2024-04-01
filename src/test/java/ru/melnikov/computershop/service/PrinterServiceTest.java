package ru.melnikov.computershop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.melnikov.computershop.exception.PrinterNotFoundException;
import ru.melnikov.computershop.repository.PrinterRepository;
import ru.melnikov.computershop.repository.ProductRepository;
import ru.melnikov.computershop.service.product.PrinterService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.melnikov.computershop.util.TestConstants.TEST_PRINTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOG;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOGLOCK;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRINTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRODUCT_DATA;

@SpringBootTest
public class PrinterServiceTest {
    @Autowired
    private PrinterService printerService;
    @Autowired
    private PrinterRepository printerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void removeTestData(){
        jdbcTemplate.execute(TRUNCATE_PRINTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @AfterEach
    void dropLiquibaseChangelogAndRemoveTestData() {
        jdbcTemplate.execute(TRUNCATE_PRINTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
        jdbcTemplate.execute(TRUNCATE_CHANGELOG);
        jdbcTemplate.execute(TRUNCATE_CHANGELOGLOCK);
    }

    @Test
    public void saveTest() {
        // Given
        var testPrinter = TEST_PRINTER;
        // When
        var result = printerService.create(testPrinter);

        // Then
        assertEquals(1, productRepository.findAll().size());
        assertEquals(1, printerRepository.findAll().size());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(testPrinter);
    }

    @Test
    public void getAllTest() {
        // Given
        var testPrinter = printerRepository.save(TEST_PRINTER);
        // When
        var result = printerService.getAll();
        // Then
        assertEquals(1, result.size());

        assertThat(result.stream().findFirst().get())
                .usingRecursiveComparison()
                .isEqualTo(testPrinter);
    }

    @Test
    public void getByIdTest(){
        // Given
        var testPrinter = printerRepository.save(TEST_PRINTER);
        // When
        var result = printerService.getById(testPrinter.getId());
        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(testPrinter);
    }

    @Test
    public void deleteByIdTest(){
        // Given
        var testPrinter = printerRepository.save(TEST_PRINTER);
        // When
        printerService.deleteById(testPrinter.getId());
        // Then
        assertTrue(printerRepository.findAll().isEmpty());

        assertThrows(PrinterNotFoundException.class, () -> printerService.getById(testPrinter.getId()));
    }
}
