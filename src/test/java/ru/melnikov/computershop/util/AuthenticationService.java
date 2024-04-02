package ru.melnikov.computershop.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import ru.melnikov.computershop.dto.user.JwtAuthenticationResponse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static ru.melnikov.computershop.util.TestConstants.SIGN_UP_URL;
import static ru.melnikov.computershop.util.TestConstants.TEST_SIGN_UP_REQUEST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationService {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Cacheable("JWT")
    @SneakyThrows
    public String getJwtToken(){
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
