package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        this.baseUrl = url + "account";
    }

    public int getAccountIdByUser(String username) {
        return 0;
    }

    public Account getAccountByUsername(String username) {
        int userId = getAccountIdByUser(username);
        return getAccountByUserId(userId);
    }

    public Account getAccountByUserId(int userId) {
        return new Account();
    }

    public Account getAccount(String token) throws AccountServiceException {
        HttpEntity<Void> entity = createRequestEntity(token);
        try {
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Account.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            String message = createBalanceExceptionMessage(e);
            throw new AccountServiceException(message);
        }
    }

    public double balanceRequest(String token) throws AccountServiceException {
         return sendBalanceRequest(createRequestEntity(token));
    }

    private double sendBalanceRequest(HttpEntity<Void> entity) throws AccountServiceException {
        try{
            ResponseEntity<Double> responseBalance = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Double.class);
            return responseBalance.getBody();
        } catch(RestClientResponseException e){
           String message = createBalanceExceptionMessage(e);
           throw new AccountServiceException(message);
        }
    }

    private String createBalanceExceptionMessage(RestClientResponseException e){
            return e.getRawStatusCode() + " : " + e.getResponseBodyAsString();
    }

    public Integer getAccountIdFromUserId(String token, int userId){
        return sendAccountIdFromUserId(createRequestEntity(token), userId);
    }

    public Integer sendAccountIdFromUserId(HttpEntity<Void> entity, Integer userId){
        ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "/" + userId, HttpMethod.POST, entity, Account.class);
        return response.getBody().getAccountId();
    }

    public Integer getAccountIdFromUsername(String token, String username) {
        return sendAccountIdFromUsername(createRequestEntity(token), username);
    }

    public Integer sendAccountIdFromUsername(HttpEntity<Void> entity, String username) {
        ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "/user/" + username, HttpMethod.POST, entity, Account.class);
        return response.getBody().getAccountId();
    }

    private HttpEntity<Void> createRequestEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Void>(headers);
    }

    private HttpEntity<Account> createRequestEntity(String token, Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Account>(account, headers);
    }

}
