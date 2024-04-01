package ru.melnikov.computershop.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import ru.melnikov.computershop.dto.PrinterDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.enumerate.PrinterType;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.mapper.PrinterMapper;
import ru.melnikov.computershop.model.product.Printer;
import ru.melnikov.computershop.service.product.PrinterService;
import ru.melnikov.computershop.util.AuthenticationService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.melnikov.computershop.util.TestConstants.BASE_PRINTER_URL;
import static ru.melnikov.computershop.util.TestConstants.BEARER_BASE;
import static ru.melnikov.computershop.util.TestConstants.PRINTER_URL;
import static ru.melnikov.computershop.util.TestConstants.SIGN_UP_URL;
import static ru.melnikov.computershop.util.TestConstants.TEST_PRINTER;
import static ru.melnikov.computershop.util.TestConstants.TEST_SIGN_UP_REQUEST;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRINTER;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRODUCT_DATA;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_USER;

@SpringBootTest
@AutoConfigureMockMvc
public class PrinterRestControllerTest {
    

    @Autowired
    private PrinterService printerService;
    @Autowired
    private PrinterMapper printerMapper;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @SneakyThrows
    void clearTables() {

        jdbcTemplate.execute(TRUNCATE_PRINTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @AfterEach
    void removeTestData() {
        jdbcTemplate.execute(TRUNCATE_USER);
        jdbcTemplate.execute(TRUNCATE_PRINTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @Test
    @SneakyThrows
    void getPrintersWithoutAuth() {

        // When
        var result = mockMvc.perform(
                get(PRINTER_URL)
        );

        // Then
        result.andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void getPrinters() {

        // When
        var result = mockMvc.perform(
                get(PRINTER_URL)
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, authenticationService.getJwtToken()))
        );

        // Then
        result.andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getPrinter() {

        // Given
        var testPrinter = printerService.create(TEST_PRINTER);

        // When
        var result = mockMvc.perform(
                get(String.format(BASE_PRINTER_URL, testPrinter.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, authenticationService.getJwtToken()))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var printerResult = objectMapper.readValue(resultDataAsString, Printer.class);

        assertThat(printerResult)
                .usingRecursiveComparison()
                .isEqualTo(testPrinter);
    }

    @Test
    @SneakyThrows
    void createPrinter() {

        // Given
        var printerPostData = PrinterDto.builder()
                .coloured(true)
                .type(PrinterType.LASER)
                .productData(ProductDto.builder()
                        .id(UUID.randomUUID())
                        .productType(ProductType.PRINTER)
                        .price(BigDecimal.valueOf(15000))
                        .modelName("some printer")
                        .build())
                .build();

        // When
        var result = mockMvc.perform(
                post(PRINTER_URL)
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, authenticationService.getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(printerPostData))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var creationResult = objectMapper.readValue(resultDataAsString, PrinterDto.class);

        assertThat(creationResult)
                .usingRecursiveComparison()
                .ignoringFields("id") // берётся из productData
                .isEqualTo(printerPostData);
    }

    @Test
    @SneakyThrows
    void updatePrinter(){

        // Given
        var testPrinter = printerService.create(TEST_PRINTER);

        var printerDtoToUpdate = printerMapper.toDto(testPrinter);

        var newPrice = testPrinter.getProductData().getPrice()
                .add(BigDecimal.valueOf(5000));

        printerDtoToUpdate.getProductData().setPrice(newPrice);

        // When
        var result = mockMvc.perform(
                put(String.format(BASE_PRINTER_URL, testPrinter.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, authenticationService.getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(printerDtoToUpdate))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var updateResult = objectMapper.readValue(resultDataAsString, PrinterDto.class);

        assertThat(updateResult)
                .usingRecursiveComparison()
                .ignoringFields("productData.price") // обновлено
                .isEqualTo(printerDtoToUpdate);

        assertEquals(newPrice, updateResult.getProductData().getPrice());
    }

    @Test
    @SneakyThrows
    void updateNonExistingPrinter(){

        // Given
        var printerDtoToUpdate = printerMapper.toDto(TEST_PRINTER);

        var newPrice = BigDecimal.valueOf(123);

        printerDtoToUpdate.getProductData().setPrice(newPrice);

        // When
        var result = mockMvc.perform(
                put(String.format(BASE_PRINTER_URL, UUID.randomUUID()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, authenticationService.getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(printerDtoToUpdate))
        );

        // Then
        result.andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void deletePrinter() {

        // Given
        var testPrinter = printerService.create(TEST_PRINTER);

        // When
        var result = mockMvc.perform(
                delete(String.format(BASE_PRINTER_URL, testPrinter.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, authenticationService.getJwtToken()))
        );

        // Then
        result.andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void deleteNonExistingPrinter() {

        // When
        var result = mockMvc.perform(
                delete(String.format(BASE_PRINTER_URL, UUID.randomUUID()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, authenticationService.getJwtToken()))
        );

        // Then
        result.andExpect(status().isNotFound());
    }
}
