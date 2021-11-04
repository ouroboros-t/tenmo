package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.openqa.selenium.remote.Response;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserService {
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public UserService(String url){
        this.baseUrl = url + "user";
    }

    public User[] getUsersRequest(HttpEntity<Void> entity) throws UserServiceException {
        User[] users = null;
        try{
            ResponseEntity<User[]> responseUsers = restTemplate.exchange(baseUrl, HttpMethod.GET,entity, User[].class);
            users = responseUsers.getBody();
            return users;
        }catch (RestClientResponseException e){
            String message = createUserExceptionMessage(e);
            throw new UserServiceException(message);
        }
    }

    private String createUserExceptionMessage(RestClientResponseException e){
        return e.getRawStatusCode() + " : " + e.getResponseBodyAsString();
    }

    private HttpEntity<Void> createRequestEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Void>(headers);
    }

}
