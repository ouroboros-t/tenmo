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

    public Account getAccount(String token) throws AccountServiceException {
        HttpEntity<Account> entity = createRequestEntity(token);
        try {
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Account.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            String message = createBalanceExceptionMessage(e);
            throw new AccountServiceException(message);
        }
    }

    public BigDecimal balanceRequest(String token) throws AccountServiceException {
         return sendBalanceRequest(createRequestEntity(token));
    }

    private BigDecimal sendBalanceRequest(HttpEntity<Account> entity) throws AccountServiceException {
        try{
            ResponseEntity<BigDecimal> responseBalance = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, BigDecimal.class);
            return responseBalance.getBody();
        } catch(RestClientResponseException e){
           String message = createBalanceExceptionMessage(e);
           throw new AccountServiceException(message);
        }
    }

    private String createBalanceExceptionMessage(RestClientResponseException e){
            return e.getRawStatusCode() + " : " + e.getResponseBodyAsString();
    }

    public Integer getAccountIdFromUserId(String token, Integer userId){
        return sendAccountIdFromUserId(createRequestEntity(token), userId);
    }

    public Integer sendAccountIdFromUserId(HttpEntity<Account> entity, Integer userId){
        ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "/" + userId, HttpMethod.POST, entity, Account.class);
        return response.getBody().getAccountId();
    }

    private HttpEntity<Account> createRequestEntity(String token) {
        Account account = new Account();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Account>(account, headers);
    }

}
