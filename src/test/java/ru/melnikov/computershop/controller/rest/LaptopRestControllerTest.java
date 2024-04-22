package ru.melnikov.computershop.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import ru.melnikov.computershop.dto.LaptopDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.dto.user.JwtAuthenticationResponse;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.mapper.LaptopMapper;
import ru.melnikov.computershop.model.product.Laptop;
import ru.melnikov.computershop.service.product.LaptopService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.melnikov.computershop.util.TestConstants.BASE_LAPTOP_URL;
import static ru.melnikov.computershop.util.TestConstants.BEARER_BASE;
import static ru.melnikov.computershop.util.TestConstants.LAPTOP_URL;
import static ru.melnikov.computershop.util.TestConstants.SIGN_UP_URL;
import static ru.melnikov.computershop.util.TestConstants.TEST_LAPTOP;
import static ru.melnikov.computershop.util.TestConstants.TEST_SIGN_UP_REQUEST;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_LAPTOP;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_PRODUCT_DATA;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_USER;


@SpringBootTest
@AutoConfigureMockMvc
public class LaptopRestControllerTest {

   // private String JWT;

    @Autowired
    private LaptopService laptopService;
    @Autowired
    private LaptopMapper laptopMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @SneakyThrows
    void clearTables() {
        jdbcTemplate.execute(TRUNCATE_LAPTOP);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);

    }

    @AfterEach
    void removeTestData() {
        jdbcTemplate.execute(TRUNCATE_USER);
        jdbcTemplate.execute(TRUNCATE_LAPTOP);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    //@Test
    @SneakyThrows
    void getLaptopsWithoutAuth() {

        // When
        var result = mockMvc.perform(
                get(LAPTOP_URL)
        );

        // Then
        result.andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void getLaptops() {

        // When
        var result = mockMvc.perform(
                get(LAPTOP_URL)
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
        );

        // Then
        result.andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getLaptop() {

        // Given
        var testLaptop = laptopService.create(TEST_LAPTOP);

        // When
        var result = mockMvc.perform(
                get(String.format(BASE_LAPTOP_URL, testLaptop.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var laptopResult = objectMapper.readValue(resultDataAsString, Laptop.class);

        assertThat(laptopResult)
                .usingRecursiveComparison()
                .isEqualTo(testLaptop);
    }

    @Test
    @SneakyThrows
    void createLaptop() {

        // Given
        var laptopPostData = LaptopDto.builder()
                .hd(256)
                .ram(8)
                .speed(2.5F)
                .screen(14.7F)
                .productData(ProductDto.builder()
                        .id(UUID.randomUUID())
                        .price(BigDecimal.valueOf(50000))
                        .modelName("test laptop")
                        .productType(ProductType.LAPTOP)
                        .build())
                .build();

        // When
        var result = mockMvc.perform(
                post(LAPTOP_URL)
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(laptopPostData))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var creationResult = objectMapper.readValue(resultDataAsString, LaptopDto.class);

        assertThat(creationResult)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id", // берётся из productData
                        "productData.id") // генерируется в сервисе
                .isEqualTo(laptopPostData);
    }

    @Test
    @SneakyThrows
    void updateLaptop(){

        // Given
        var testLaptop = laptopService.create(TEST_LAPTOP);

        var laptopDtoToUpdate = laptopMapper.toDto(testLaptop);

        var newPrice = testLaptop.getProductData().getPrice()
                .add(BigDecimal.valueOf(5000));

        laptopDtoToUpdate.getProductData().setPrice(newPrice);

        // When
        var result = mockMvc.perform(
                put(String.format(BASE_LAPTOP_URL, testLaptop.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(laptopDtoToUpdate))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var updateResult = objectMapper.readValue(resultDataAsString, LaptopDto.class);

        assertThat(updateResult)
                .usingRecursiveComparison()
                .ignoringFields("productData.price") // обновлено
                .isEqualTo(laptopDtoToUpdate);

        assertEquals(newPrice, updateResult.getProductData().getPrice());
    }

    @Test
    @SneakyThrows
    void updateNonExistingLaptop(){

        // Given
        var laptopDtoToUpdate = laptopMapper.toDto(TEST_LAPTOP);

        var newPrice = BigDecimal.valueOf(123);

        laptopDtoToUpdate.getProductData().setPrice(newPrice);

        // When
        var result = mockMvc.perform(
                put(String.format(BASE_LAPTOP_URL, UUID.randomUUID()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(laptopDtoToUpdate))
        );

        // Then
        result.andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void deleteLaptop() {

        // Given
        var testLaptop = laptopService.create(TEST_LAPTOP);

        // When
        var result = mockMvc.perform(
                delete(String.format(BASE_LAPTOP_URL, testLaptop.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
        );

        // Then
        result.andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void deleteNonExistingLaptop() {

        // When
        var result = mockMvc.perform(
                delete(String.format(BASE_LAPTOP_URL, UUID.randomUUID()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
        );

        // Then
        result.andExpect(status().isNotFound());
    }

    @Cacheable("JWT")
    @SneakyThrows
    private String getJwtToken(){
        var singUpResponse = mockMvc.perform(
                post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_SIGN_UP_REQUEST))
        ).andReturn();

        var singUpResponseContent = singUpResponse.getResponse().getContentAsString();
        var jwtResponse = objectMapper.readValue(singUpResponseContent, JwtAuthenticationResponse.class);
        return jwtResponse.getToken();
    }
}
