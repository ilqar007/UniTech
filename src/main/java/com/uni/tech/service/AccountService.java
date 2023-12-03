package com.uni.tech.service;

import com.uni.tech.dto.DeactivateRequest;
import com.uni.tech.entity.Account;
import com.uni.tech.entity.User;

import java.util.List;

public interface AccountService {

    public Account createAccount(User user,double balance);
    public void makeTransfer(User loggedUser,String sourceAccountNumber, String targetAccountNumber, double amount);

    void deactivateAccount(User getLoggedinUser, String accountNumber);


    List<Account> listActiveAccounts(User getLoggedinUser);

}

