package com.uni.tech;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CurrencyRatesControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void ableToSeeSelectedCurrencyRate() throws JSONException {
        String url ="http://localhost:" + port + "/rest/currencyRates/USD/AZN";
        HttpEntity<Map<String,String>> entity = new HttpEntity<>(null,GetHttpHeaders());
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET,entity, String.class,Map.of());
        JSONObject res= new JSONObject(response.getBody());
        assertThat(res.getDouble("msg")).isEqualTo (1.7);
    }


    private HttpHeaders GetHttpHeaders() throws JSONException {
        this.restTemplate.postForObject("http://localhost:" + port + "/rest/auth/register", Map.of("pin","123456","password","12345678"),String.class);
        String responseString = this.restTemplate.postForObject("http://localhost:" + port + "/rest/auth/login", Map.of("pin","123456","password","12345678"), String.class);
        JSONObject jsonObject = new JSONObject(responseString);
        String url ="http://localhost:" + port + "/rest/accounts";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jsonObject.getString("token"));
        return headers;
    }
}
