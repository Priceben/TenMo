package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class ClientService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public ClientService(String API_BASE_URL){
        this.API_BASE_URL = API_BASE_URL;
        this.currentUser = currentUser;
    }

    public double getBalance(AuthenticatedUser currentUser){
        double balance;
        try {
            balance = restTemplate.exchange(API_BASE_URL + "/accounts/" + currentUser.getUser().getId() +"/balance",
                    HttpMethod.GET, makeAuthEntity(currentUser), Double.class).getBody();
            System.out.println("Your current account balance is: $" + balance);
        } catch (Exception e){
            System.out.println("This happened: " + e.getMessage() + " and" + e.getCause());
            balance = 0;
        }
        return balance;
    }

    public Transfers[] getAllTransfers(AuthenticatedUser currentUser){
        Transfers[] transfersList = null;
        try{
            transfersList = restTemplate.exchange(API_BASE_URL + "/accounts/transfers/all",
                    HttpMethod.GET, makeAuthEntity(currentUser), Transfers[].class).getBody();
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

    public User[] listUsers(){
        User[] userList = null;
        userList= restTemplate.exchange(API_BASE_URL + "/listUsers", HttpMethod.GET,makeAuthEntity(currentUser), User[].class).getBody();
        for(User user : userList){
            System.out.println(user);
        }
        return userList;

    }



    public Transfers sendMoney(Transfers transfer, AuthenticatedUser currentUser){

        try{
            transfer = restTemplate.exchange(API_BASE_URL + "/accounts/transfers",
                    HttpMethod.POST, makeTransferEntity(transfer, currentUser), Transfers.class).getBody();
        } catch (NullPointerException ne){
            System.out.println("No transfers found");
        } catch (RestClientException re){
            System.out.println("Request invalid");
        } catch (ResponseStatusException rse){
            System.out.println("Error contacting server");
        } catch (Exception e){
            System.out.println("Oops, something went wrong!");
        }
        return transfer;
    }

    public int getAccountIdByUserId(int userId, AuthenticatedUser currentUser){
        int account = 0;
        try {
            account = restTemplate.exchange(API_BASE_URL + "accounts/" + userId, HttpMethod.GET, makeAuthEntity(currentUser), Integer.class).getBody();
        } catch (Exception e){
            System.out.println("This happened: " + e.getMessage() + " and " + e.getCause());
        }
        return account;
    }



    private HttpEntity makeAuthEntity(AuthenticatedUser currentUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
    private  HttpEntity makeTransferEntity(Transfers transfer, AuthenticatedUser currentUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfers> entity =new HttpEntity<>(transfer, headers);
        return entity;
    }
}
