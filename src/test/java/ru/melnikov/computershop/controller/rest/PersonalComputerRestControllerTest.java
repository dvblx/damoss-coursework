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
import ru.melnikov.computershop.dto.PersonalComputerDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.dto.user.JwtAuthenticationResponse;
import ru.melnikov.computershop.enumerate.CdType;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.mapper.PersonalComputerMapper;
import ru.melnikov.computershop.model.product.PersonalComputer;
import ru.melnikov.computershop.service.product.PersonalComputerService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.melnikov.computershop.util.TestConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonalComputerRestControllerTest {

    @Autowired
    private PersonalComputerService computerService;
    @Autowired
    private PersonalComputerMapper computerMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    @SneakyThrows
    void clearTables() {

        jdbcTemplate.execute(TRUNCATE_PERSONAL_COMPUTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @AfterEach
    void removeTestData() {
        jdbcTemplate.execute(TRUNCATE_USER);
        jdbcTemplate.execute(TRUNCATE_PERSONAL_COMPUTER);
        jdbcTemplate.execute(TRUNCATE_PRODUCT_DATA);
    }

    @Test
    @SneakyThrows
    void getComputersWithoutAuth() {

        // When
        var result = mockMvc.perform(
                get(PERSONAL_COMPUTER_URL)
        );

        // Then
        result.andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void getComputers() {

        // When
        var result = mockMvc.perform(
                get(PERSONAL_COMPUTER_URL)
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
        );

        // Then
        result.andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void getComputer() {

        // Given
        var testComputer = computerService.create(TEST_PERSONAL_COMPUTER);

        // When
        var result = mockMvc.perform(
                get(String.format(BASE_PERSONAL_COMPUTER_URL, testComputer.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var computerResult = objectMapper.readValue(resultDataAsString, PersonalComputer.class);

        assertThat(computerResult)
                .usingRecursiveComparison()
                .isEqualTo(testComputer);
    }

    @Test
    @SneakyThrows
    void createComputer() {

        // Given
        var computerPostData = PersonalComputerDto.builder()
                .ram(16)
                .hd(512)
                .speed(2.5F)
                .cdType(CdType.SSD)
                .productData(ProductDto.builder()
                        .id(UUID.randomUUID())
                        .productType(ProductType.PERSONAL_COMPUTER)
                        .modelName("some pc")
                        .price(BigDecimal.valueOf(80000))
                        .build())
                .build();

        // When
        var result = mockMvc.perform(
                post(PERSONAL_COMPUTER_URL)
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(computerPostData))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var creationResult = objectMapper.readValue(resultDataAsString, PersonalComputerDto.class);

        assertThat(creationResult)
                .usingRecursiveComparison()
                .ignoringFields(
                        "id", // берётся из productData
                        "productData.id") // генерируется в сервисе
                .isEqualTo(computerPostData);
    }

    @Test
    @SneakyThrows
    void updateComputer() {

        // Given
        var testComputer = computerService.create(TEST_PERSONAL_COMPUTER);

        var computerDtoToUpdate = computerMapper.toDto(testComputer);

        var newPrice = testComputer.getProductData().getPrice()
                .subtract(BigDecimal.valueOf(5000));

        computerDtoToUpdate.getProductData().setPrice(newPrice);

        // When
        var result = mockMvc.perform(
                put(String.format(BASE_PERSONAL_COMPUTER_URL, testComputer.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(computerDtoToUpdate))
        );

        // Then
        result.andExpect(status().isOk());

        var resultDataAsString = result.andReturn()
                .getResponse()
                .getContentAsString();

        var updateResult = objectMapper.readValue(resultDataAsString, PersonalComputerDto.class);

        assertThat(updateResult)
                .usingRecursiveComparison()
                .ignoringFields("productData.price") // обновлено
                .isEqualTo(computerDtoToUpdate);

        assertEquals(newPrice, updateResult.getProductData().getPrice());
    }

    @Test
    @SneakyThrows
    void updateNonExistingComputer() {

        // Given
        var computerDtoToUpdate = computerMapper.toDto(TEST_PERSONAL_COMPUTER);

        var newPrice = BigDecimal.valueOf(5000);

        computerDtoToUpdate.getProductData().setPrice(newPrice);

        // When
        var result = mockMvc.perform(
                put(String.format(BASE_PERSONAL_COMPUTER_URL, UUID.randomUUID()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(computerDtoToUpdate))
        );

        // Then
        result.andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void deleteComputer() {

        // Given
        var testComputer = computerService.create(TEST_PERSONAL_COMPUTER);

        // When
        var result = mockMvc.perform(
                delete(String.format(BASE_PERSONAL_COMPUTER_URL, testComputer.getId()))
                        .header(HttpHeaders.AUTHORIZATION, String.format(BEARER_BASE, getJwtToken()))
        );

        // Then
        result.andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void deleteNonExistingComputer() {

        // When
        var result = mockMvc.perform(
                delete(String.format(BASE_PERSONAL_COMPUTER_URL, UUID.randomUUID()))
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
