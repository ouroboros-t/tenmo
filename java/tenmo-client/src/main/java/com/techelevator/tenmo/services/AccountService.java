package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        this.baseUrl = url + "account";
    }

    public BigDecimal balanceRequest(AuthenticatedUser currentUser) throws BalanceServiceException {

         return sendBalanceRequest(createRequestEntity(currentUser));

    }

    private BigDecimal sendBalanceRequest(HttpEntity<AuthenticatedUser> entity) throws BalanceServiceException {
        try{
            ResponseEntity<BigDecimal> responseBalance = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, BigDecimal.class);
            return responseBalance.getBody();
        } catch(RestClientResponseException e){
           String message = createBalanceExceptionMessage(e);
           throw new BalanceServiceException(message);
        }
    }

    private String createBalanceExceptionMessage(RestClientResponseException e){
            return e.getRawStatusCode() + " : " + e.getResponseBodyAsString();
    }

    private HttpEntity<AuthenticatedUser> createRequestEntity(AuthenticatedUser currentUser) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<AuthenticatedUser> entity = new HttpEntity<AuthenticatedUser>(headers);
        return entity;
    }

}
