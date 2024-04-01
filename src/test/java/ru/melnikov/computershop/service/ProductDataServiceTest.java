package ru.melnikov.computershop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.model.product.ProductData;
import ru.melnikov.computershop.repository.LaptopRepository;
import ru.melnikov.computershop.repository.PersonalComputerRepository;
import ru.melnikov.computershop.repository.PrinterRepository;
import ru.melnikov.computershop.repository.ProductRepository;
import ru.melnikov.computershop.service.product.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class ProductDataServiceTest {

    @Autowired
    private PersonalComputerRepository personalComputerRepository;
    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private PrinterRepository printerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void removeTestData(){
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
    public void getAllTest() {
        //Given
        productRepository.save(ProductData.builder()
                .id(UUID.randomUUID())
                .modelName("some model name")
                .price(BigDecimal.valueOf(1000))
                .productType(ProductType.PRINTER)
                .build());
        //When
        var products = productService.getAll();

        // Then
        assertEquals(1, products.size());

        assertThat(products)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(productRepository.findAll());

    }

    @Test
    void filterByType(){
        // Given
        printerRepository.save(TEST_PRINTER);
        laptopRepository.save(TEST_LAPTOP);
        personalComputerRepository.save(TEST_PERSONAL_COMPUTER);

        // When
        var printers = productRepository.findByProductType(ProductType.PRINTER);
        var laptops = productRepository.findByProductType(ProductType.LAPTOP);
        var computers = productRepository.findByProductType(ProductType.PERSONAL_COMPUTER);

        assertThat(computers)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(productRepository.findByProductType(ProductType.PERSONAL_COMPUTER));

        assertThat(laptops)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(productRepository.findByProductType(ProductType.LAPTOP));

        assertThat(printers)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(productRepository.findByProductType(ProductType.PRINTER));
    }
}
