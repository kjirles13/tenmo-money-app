package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public AccountService(String url) {
        this.baseUrl = url;
    }

    public void setCurrentUser(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }

    public User[] getAllUsers() {
        User[] users = null;

        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(baseUrl + "users",
                            HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public Account getCurrentUserAccount() {
        Account currentAccount = null;

        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(baseUrl + "accounts/current",
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            currentAccount = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return currentAccount;
    }

    public Transfer makeTransfer(Transfer transfer){
        Transfer returnedTransfer = null;

        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(baseUrl + "accounts/transfer", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
            returnedTransfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
        }
        return returnedTransfer;
    }

    public Transfer makeRequest(Transfer transfer){
        Transfer returnedTransfer = null;
        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(baseUrl + "accounts/request", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
            returnedTransfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
        }
        return returnedTransfer;
    }

    public Transfer[] getAllTransfers() {
        Transfer[] transfers = null;

        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(baseUrl + "accounts/transfer-list",
                            HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfers;
    }

    public Transfer getTransferById(Transfer transfer) {
        Transfer returnedTransfer = null;

        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(baseUrl + "accounts/transfer/id",
                            HttpMethod.POST, makeTransferEntity(transfer), Transfer.class);
            returnedTransfer = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
        }
        return returnedTransfer;
    }

    public Transfer[] getAllPendingRequests() {
        Transfer[] transfers = null;

        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(baseUrl + "accounts/request/pending",
                            HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfers;
    }

    public Transfer updateRequest(Transfer transfer){
        Transfer returnedRequest = null;

        try {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(baseUrl + "accounts/request/update", HttpMethod.PUT, makeTransferEntity(transfer), Transfer.class);
            returnedRequest = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
        }
        return returnedRequest;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }
}
