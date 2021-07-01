package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;


public class ClientService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public ClientService(String API_BASE_URL){
        this.API_BASE_URL = API_BASE_URL;
    }

    public BigDecimal getBalance(AuthenticatedUser currentUser){
        BigDecimal balance;
        try {
            balance = restTemplate.exchange(API_BASE_URL + "/accounts/" + currentUser.getUser().getId() +"/balance",
                    HttpMethod.GET, makeAuthEntity(currentUser), BigDecimal.class).getBody();
            System.out.println("Your current account balance is: $" + balance);
        } catch (Exception e){
            System.out.println("This happened: " + e.getMessage() + " and" + e.getCause());
            balance = new BigDecimal("0");
        }
        return balance;
    }

    public Transfers[] getAllTransfers(AuthenticatedUser currentUser){
        Transfers[] transfersList = null;
        try{
            transfersList = restTemplate.exchange(API_BASE_URL + "/accounts/" + currentUser.getUser().getId() +
                    "/transfers", HttpMethod.GET, makeAuthEntity(currentUser), Transfers[].class).getBody();
        } catch (NullPointerException ne){
            System.out.println("No transfers found");
        } catch (RestClientException re){
            System.out.println("Request invalid");
        } catch (ResponseStatusException rse){
            System.out.println("Error contacting server");
        } catch (Exception e){
            System.out.println("Oops, something went wrong!");
        }
        return transfersList;
    }



    private HttpEntity makeAuthEntity(AuthenticatedUser currentUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}
