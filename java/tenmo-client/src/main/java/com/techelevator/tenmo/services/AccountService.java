package com.techelevator.tenmo.services;

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

    public BigDecimal balanceRequest(String token) throws AccountServiceException {
         return sendBalanceRequest(createRequestEntity(token));
    }

    private BigDecimal sendBalanceRequest(HttpEntity<Void> entity) throws AccountServiceException {
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

    private HttpEntity<Void> createRequestEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Void>(headers);
    }

}
