package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.openqa.selenium.remote.Response;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String url) {
        this.baseUrl = url + "transfer";
    }

    public Transfer transferRequest(String token, Transfer transfer) throws TransferServiceException {
        return sendTransferRequest(createTransferEntity(token, transfer));

    }

    public Transfer sendTransferRequest(HttpEntity<Transfer> entity) throws TransferServiceException {
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Transfer.class);
            return response.getBody();
        } catch (RestClientResponseException e) {
            String message = createTransferExceptionMessage(e);
            throw new TransferServiceException(message);
        }

    }


    private String createTransferExceptionMessage(RestClientResponseException e) {
        return e.getRawStatusCode() + " : " + e.getResponseBodyAsString();
    }


    private HttpEntity<Transfer> createTransferEntity(String token, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Transfer>(transfer, headers);
    }

    //TODO: Display is incomplete - need to be able to retrieve the user name as well as the transfer info.

    public Transfer[] transfersRequest(String token) throws TransferServiceException {
        return sendTransfersRequest(createRequestEntity(token));
    }

    public Transfer[] sendTransfersRequest(HttpEntity<Void> entity) throws TransferServiceException {
        Transfer[] transfers = null;
        try{
            ResponseEntity<Transfer[]> responseTransfers = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Transfer[].class);
            transfers = responseTransfers.getBody();
            return transfers;
        }catch (RestClientResponseException e) {
            String message = createTransferExceptionMessage(e);
            throw new TransferServiceException(message);
        }

    }

    private HttpEntity<Void> createRequestEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<Void>(headers);
    }







}
