package com.uni.tech;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
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
public class AccountsControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private  HttpHeaders GetHttpHeaders() throws JSONException {
        this.restTemplate.postForObject("http://localhost:" + port + "/rest/auth/register", Map.of("pin","12345","password","12345678"),String.class);
        String responseString = this.restTemplate.postForObject("http://localhost:" + port + "/rest/auth/login", Map.of("pin","12345","password","12345678"), String.class);
        JSONObject jsonObject = new JSONObject(responseString);
        String url ="http://localhost:" + port + "/rest/accounts";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jsonObject.getString("token"));
        return headers;
    }
    @Test
    void createAccount() throws JSONException {
        assertThat(addNewAccoount().getString("msg")).contains("Account created");
    }

    private JSONObject addNewAccoount() throws JSONException {
        String url ="http://localhost:" + port + "/rest/accounts/createAccount";
        HttpEntity<Map<String,String>> entity = new HttpEntity<>(Map.of("balance","50"),GetHttpHeaders());
        return new JSONObject(this.restTemplate.postForObject(url,  entity, String.class));
    }

    @Test
    void seeActiveAccounts() throws JSONException {
        assertThat(listActiveAccounts().getJSONArray("msg").length()).isEqualTo (2);
    }

    private JSONObject listActiveAccounts() throws JSONException {
        // list active accounts
        String url ="http://localhost:" + port + "/rest/accounts";
        HttpEntity<Map<String,String>> entity = new HttpEntity<>(null,GetHttpHeaders());
        ResponseEntity<String> response = this.restTemplate.exchange(url,HttpMethod.GET,entity, String.class,Map.of());
        return new JSONObject(response.getBody());
    }

    @Test
    void ableToMakeTransferCase1() throws JSONException {
       addNewAccoount();
       addNewAccoount();
       JSONArray activeAccounts =  listActiveAccounts().getJSONArray("msg");
       String accFrom= activeAccounts.getString(0);
       String accTo = activeAccounts.getString(1);
       JSONObject response = new JSONObject(makeTransfer(accFrom,accTo,20));
       assertThat(response.getString("msg")).contains("Transferred successfully");
    }

    @Test
    void ableToMakeTransferCase2() throws JSONException {
        JSONArray activeAccounts =  listActiveAccounts().getJSONArray("msg");
        String accFrom= activeAccounts.getString(0);
        String accTo = activeAccounts.getString(1);
        String response = makeTransfer(accFrom,accTo,50);
        assertThat(response).contains("Insufficient balance");
    }

    @Test
    void ableToMakeTransferCase3() throws JSONException {
        JSONArray activeAccounts =  listActiveAccounts().getJSONArray("msg");
        String accFrom= activeAccounts.getString(0);
        String response = makeTransfer(accFrom,accFrom,50);
        assertThat(response).contains("Transfer to same account");
    }

    @Test
    void ableToMakeTransferCase4() throws JSONException {
        JSONArray activeAccounts =  listActiveAccounts().getJSONArray("msg");
        String accFrom= activeAccounts.getString(0);
        String accTo = activeAccounts.getString(1);
        deactivateAccount(accTo);
        String response = makeTransfer(accFrom,accTo,50);
        assertThat(response).contains("Target account is deactive");
    }

    @Test
    void ableToMakeTransferCase5() throws JSONException {
        JSONArray activeAccounts =  listActiveAccounts().getJSONArray("msg");
        String accFrom= activeAccounts.getString(0);
        String response = makeTransfer(accFrom,"--------",50);
        assertThat(response).contains("Target account not found");
    }

    private String makeTransfer(String accFrom,String accTo,double amount) throws JSONException {
        String url ="http://localhost:" + port + "/rest/accounts/makeTransfer";
        HttpEntity<Map<String,String>> entity = new HttpEntity<>(Map.of("sourceAccountNumber",accFrom,"targetAccountNumber",accTo,"amount",amount+""),GetHttpHeaders());
        return this.restTemplate.postForObject(url,  entity, String.class);
    }

    private void deactivateAccount(String accountNumber) throws JSONException {
        String url ="http://localhost:" + port + "/rest/accounts/deactivateAccount";
        HttpEntity<Map<String,String>> entity = new HttpEntity<>(Map.of("accountNumber",accountNumber),GetHttpHeaders());
        this.restTemplate.postForObject(url,  entity, String.class);
    }

}