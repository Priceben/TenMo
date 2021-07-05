package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;




public class ClientService {

    private String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;


    public ClientService(String API_BASE_URL){
        this.API_BASE_URL = API_BASE_URL;
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

    public void getAllTransfers(AuthenticatedUser currentUser){
        Transfers[] transfersList = null;
        System.out.println("--------------------------------------------");
        System.out.println("Transfers");
        System.out.println("Id              From/To              Amount");
        System.out.println("--------------------------------------------");
        System.out.println();
        int accountId = getAccountIdByUserId(currentUser.getUser().getId(), currentUser);

        try{
            transfersList = restTemplate.exchange(API_BASE_URL + "/accounts/transfers/" + accountId,
                    HttpMethod.GET, makeAuthEntity(currentUser), Transfers[].class).getBody();
            for(Transfers transfer : transfersList){
                System.out.println(transfer.getTransferId() + "           " + transfer.getUserFrom() + "              "
                + transfer.getAmount());
            }
        } catch (NullPointerException ne){
            System.out.println("No transfers found");
        } catch (RestClientException re){
            System.out.println("Request invalid");
        } catch (ResponseStatusException rse){
            System.out.println("Error contacting server");
        } catch (Exception e){
            System.out.println("Oops, something went wrong!");
        }
        System.out.println();
        System.out.println("--------------------------------------------------");
    }

    public void listUsers(AuthenticatedUser currentUser){
        User[] userList = null;
        try {
            userList = restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(currentUser), User[].class).getBody();
            for (User user : userList) {
                if(user.getId() != currentUser.getUser().getId()){
                    System.out.println(user.getId() + " " + user.getUsername());
                }

            }
            System.out.println();
        }catch (RestClientException rce){
            System.out.println("Bad Request");
        }catch (Exception e){
            System.out.println("Oops, something went wrong " + e.getMessage());
        }
    }

    public int userToIdInput(ConsoleService console, AuthenticatedUser currentUser){
        int userToId = console.getUserInputInteger("Enter ID of user you are sending to (0 to cancel)");
        return userToId;
    }


    public double userAmountInput(ConsoleService console, AuthenticatedUser currentUser){
        double amount = Double.parseDouble(console.getUserInput("Enter amount"));
        return amount;
    }

    public Transfers makeTransfer(AuthenticatedUser currentUser, int accountToId, double amount){

        Transfers transfer = new Transfers();
        transfer.setTransferTypeId(2);
        transfer.setTransferStatusId(2);
        transfer.setAmount(amount);
        transfer.setUserFrom(currentUser.getUser().getUsername());
        transfer.setAccountFromId(getAccountIdByUserId(currentUser.getUser().getId(), currentUser));
        transfer.setAccountToId(accountToId);
        return transfer;
    }

    public Transfers sendMoney(Transfers transfer, AuthenticatedUser currentUser){
        try{
            transfer = restTemplate.exchange(API_BASE_URL + "/accounts/transfers",
                    HttpMethod.POST, makeTransferEntity(transfer, currentUser), Transfers.class).getBody();
            System.out.println("Transfer Completed!");
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
        int accountToId = 0;
        try {
            accountToId = restTemplate.exchange(API_BASE_URL + "accounts/" + userId, HttpMethod.GET, makeAuthEntity(currentUser), Integer.class).getBody();
        } catch (Exception e){
            System.out.println("This happened: " + e.getMessage() + " and " + e.getCause());
        }
        return accountToId;
    }

    public void getTransferById(ConsoleService consoleService, AuthenticatedUser currentUser){
        Transfers transfer = new Transfers();
        int transferId = consoleService.getUserInputInteger("Enter ID of user you are sending to (0 to cancel)");
        if(transferId == 0){
            return;
        } else {
            System.out.println("");
            System.out.println("-------------------------------------------------------");
            System.out.println("Transfer Details");
            System.out.println("-------------------------------------------------------");
            transfer = restTemplate.exchange(API_BASE_URL + "/transfers/" + transferId, HttpMethod.GET, makeAuthEntity(currentUser), Transfers.class).getBody();
            System.out.println("Id:        " + transfer.getTransferId());
            System.out.println("From:      " + transfer.getUserFrom());
            System.out.println("To:        " + transfer.getUserTo());
            System.out.println("Type:      " + transfer.getTransferTypeDesc());
            System.out.println("Status:    " + transfer.getTransferStatusDesc());
            System.out.println("Amount:   $" + transfer.getAmount());
        }
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
