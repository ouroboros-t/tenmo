package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.openqa.selenium.remote.Response;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
        private String baseUrl;
        private RestTemplate restTemplate;

        public TransferService(String url){
            this.baseUrl = url + "transfer";
        }

    public boolean transferRequest(String token, Transfer transfer) throws TransferServiceException {
        return sendTransferRequest(createTransferEntity(token, transfer));

    }

    public boolean sendTransferRequest(HttpEntity<Transfer> entity) throws TransferServiceException{
        try{
            ResponseEntity<Void> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Void.class);
            return true;
        }catch(RestClientResponseException e){
            String message = createTransferExceptionMessage(e);
            throw new TransferServiceException(message);
        }

    }

    private String createTransferExceptionMessage(RestClientResponseException e){
        return e.getRawStatusCode() + " : " + e.getResponseBodyAsString();
    }


    private HttpEntity<Transfer> createTransferEntity(String token, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Transfer>(transfer,headers);
    }

}
