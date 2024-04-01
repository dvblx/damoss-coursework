package ru.melnikov.computershop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.melnikov.computershop.enumerate.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.melnikov.computershop.util.TestConstants.TEST_LAPTOP;
import static ru.melnikov.computershop.util.TestConstants.TEST_PERSONAL_COMPUTER;
import static ru.melnikov.computershop.util.TestConstants.TEST_PRINTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOG;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_CHANGELOGLOCK;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_LAPTOP;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PERSONAL_COMPUTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRINTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRODUCT_DATA;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private PersonalComputerRepository personalComputerRepository;
    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private PrinterRepository printerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void removeTestData() {
        jdbcTemplate.execute(TRUNCATE_PERSONAL_COMPUTER);
        jdbcTemplate.execute(TRUNCATE_LAPTOP);
        jdbcTemplate.execute(TRUNCATE_PRINTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @AfterEach
    void dropLiquibaseChangelogAndRemoveTestData() {
        jdbcTemplate.execute(TRUNCATE_PERSONAL_COMPUTER);
        jdbcTemplate.execute(TRUNCATE_LAPTOP);
        jdbcTemplate.execute(TRUNCATE_PRINTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
        jdbcTemplate.execute(TRUNCATE_CHANGELOG);
        jdbcTemplate.execute(TRUNCATE_CHANGELOGLOCK);
    }

    @Test
    void findByProductType() {
        // Given
        var testPrinter = printerRepository.save(TEST_PRINTER);
        var testLaptop = laptopRepository.save(TEST_LAPTOP);
        var testComputer = personalComputerRepository.save(TEST_PERSONAL_COMPUTER);

        // When
        var printers = productRepository.findByProductType(ProductType.PRINTER);
        var laptops = productRepository.findByProductType(ProductType.LAPTOP);
        var computers = productRepository.findByProductType(ProductType.PERSONAL_COMPUTER);

        // Then
        assertThat(printers)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(testPrinter.getProductData()));

        assertThat(laptops)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(testLaptop.getProductData()));

        assertThat(computers)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(List.of(testComputer.getProductData()));

    }
}
