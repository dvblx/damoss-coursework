package ru.melnikov.computershop.controller.rest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.melnikov.computershop.util.TestConstants.BASE_API_URL;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String PRODUCT_URL = "product";

    //@Test
    @SneakyThrows
    public void GetProductsWithoutAuthentication(){

        // When
        var result = mockMvc.perform(get(String.format(BASE_API_URL, PRODUCT_URL)));

        // Then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    @SneakyThrows
    public void GetProductsWithAuthentication(){

        // When
        var result = mockMvc.perform(get(String.format(BASE_API_URL, PRODUCT_URL)));

        // Then
        result.andExpect(status().isOk());
    }
}
