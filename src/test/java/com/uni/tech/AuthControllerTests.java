package com.uni.tech;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void registerShouldReturnDefaultMessage()  {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/rest/auth/register", Map.of("pin","1234","password","12345678"),String.class)).contains("User is registered successfully!");
    }

    @Test
    void registerShouldReturnErrorMessageForAlreadyRegisteredPin() {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/rest/auth/register", Map.of("pin","1234","password","12345678"),String.class)).contains("User pin is already exist!");
    }

    @Test
    void ableToLoginWithPinAndPassord()
    {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/rest/auth/login", Map.of("pin","1234","password","12345678"),String.class)).startsWith("{\"pin\":\"1234\",\"token\":");
    }

    @Test
    void ShouldReturnErrorMessageWithWrongCredentials()
    {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/rest/auth/login", Map.of("pin","1234","password","1234"),String.class)).startsWith("{\"httpStatus\":\"BAD_REQUEST\",\"message\":\"Invalid pin or password\"}");
    }
}