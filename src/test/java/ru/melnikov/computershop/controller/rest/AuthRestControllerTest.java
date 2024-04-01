package ru.melnikov.computershop.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import ru.melnikov.computershop.dto.user.SignInRequest;
import ru.melnikov.computershop.dto.user.SignUpRequest;
import ru.melnikov.computershop.repository.UserRepository;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.melnikov.computershop.util.TestConstants.BASE_API_URL;
import static ru.melnikov.computershop.util.TestConstants.SIGN_IN_URL;
import static ru.melnikov.computershop.util.TestConstants.SIGN_UP_URL;
import static ru.melnikov.computershop.util.TestConstants.TEST_SIGN_UP_REQUEST;
import static ru.melnikov.computershop.util.TestConstants.TRUNCATE_USER;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthRestControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void removeData() {
        jdbcTemplate.execute(TRUNCATE_USER);
    }

    @AfterEach
    void removeTestData() {
        jdbcTemplate.execute(TRUNCATE_USER);
    }

    @Test
    @SneakyThrows
    void signUp() {
        // Given
        var signUpRequest = SignUpRequest.builder()
                .username("username123")
                .password("password123")
                .email("foo@bar.com")
                .build();

        // When
        var result = mockMvc.perform(
                post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest))
        );

        // Then
        result.andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void signUpExistingUser() {
        // Given
        mockMvc.perform(
                post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_SIGN_UP_REQUEST))
        );

        // When
        var result = mockMvc.perform(
                post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_SIGN_UP_REQUEST))
        );

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Пользователь с таким именем уже существует"));
    }

    @Test
    @SneakyThrows
    void signUpInvalidRequestBody() {
        // Given
        var invalidSignUpRequest = SignUpRequest.builder()
                .username("")
                .password("")
                .email("")
                .build();

        // When
        var result = mockMvc.perform(
                post(SIGN_IN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSignUpRequest))
        );

        // Then
        result.andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Invalid request content")));
    }

    @Test
    @SneakyThrows
    void signIn() {
        // Given
        mockMvc.perform(
                post(SIGN_UP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TEST_SIGN_UP_REQUEST))
        );

        var signInRequest = SignInRequest.builder()
                .username(TEST_SIGN_UP_REQUEST.getUsername())
                .password(TEST_SIGN_UP_REQUEST.getPassword())
                .build();

        // When
        var result = mockMvc.perform(
                post(SIGN_IN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest))
        );

        // Then
        result.andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void signInNonExistingUser() {
        // Given
        var nonExistingUser = SignInRequest.builder()
                .username("Non")
                .password("Existing")
                .build();

        // When
        var result = mockMvc.perform(
                post(SIGN_IN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistingUser))
        );

        result.andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void signInInvalidRequestBody() {
        // Given
        var invalidSignInRequest = SignInRequest.builder()
                .username("")
                .password("")
                .build();

        // When
        var result = mockMvc.perform(
                post(SIGN_IN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSignInRequest))
        );

        result.andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Invalid request content")));
    }
}
