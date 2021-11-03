package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String baseUrl;
    private String token;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url, String token) {
        this.baseUrl = url + "/account";
        this.token = token;
    }

    public BigDecimal getBalance() {

    }

    private void createRequestEntity(String token) {

    }

}
