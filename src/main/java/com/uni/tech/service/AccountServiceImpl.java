package com.uni.tech.service;

import com.uni.tech.entity.Account;
import com.uni.tech.entity.User;
import com.uni.tech.exception.InsufficientBalanceException;
import com.uni.tech.exception.NotFoundException;
import com.uni.tech.exception.TargetAccoountDeactiveException;
import com.uni.tech.exception.TransferToSameAccountException;
import com.uni.tech.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;




    @Override
    public Account createAccount(User user,double balance) {
        String accountNumber = generateUniqueAccountNumber();
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(balance);
        account.setUser(user);
        account.setAccountStatus(Account.AccountStatus.ACTIVE);
        return accountRepository.save(account);
    }


    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            // Generate a UUID as the account number
            accountNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        } while (accountRepository.findByAccountNumber(accountNumber) != null);

        return accountNumber;
    }

    @Override
    public void makeTransfer(User loggedUser,String sourceAccountNumber, String targetAccountNumber, double amount) {

        Account sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber);
        if (loggedUser!= sourceAccount.getUser() || sourceAccount == null) {
            throw new NotFoundException("Source account not found");
        }

        Account targetAccount = accountRepository.findByAccountNumber(targetAccountNumber);
        if (targetAccount == null) {
            throw new NotFoundException("Target account not found");
        }

        if (targetAccount.getAccountStatus() == Account.AccountStatus.DEACTIVE) {
            throw new TargetAccoountDeactiveException("Target account is deactive");
        }

        if(sourceAccount == targetAccount)
        {
            throw new TransferToSameAccountException("Transfer to same account");
        }

        double sourceBalance = sourceAccount.getBalance();
        if (sourceBalance < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        double newSourceBalance = sourceBalance - amount;
        sourceAccount.setBalance(newSourceBalance);
        accountRepository.save(sourceAccount);

        double targetBalance = targetAccount.getBalance();
        double newTargetBalance = targetBalance + amount;
        targetAccount.setBalance(newTargetBalance);
        accountRepository.save(targetAccount);
    }

    @Override
    public void deactivateAccount(User loggedinUser, String accountNumber) {
        Account sourceAccount = accountRepository.findByAccountNumber(accountNumber);
        if (loggedinUser!= sourceAccount.getUser() || sourceAccount == null) {
            throw new NotFoundException("Source account not found");
        }
        sourceAccount.setAccountStatus(Account.AccountStatus.DEACTIVE);
        accountRepository.save(sourceAccount);
    }

    @Override
    public List<Account> listActiveAccounts(User user) {
        return accountRepository.findByUserAndAccountStatus(user, Account.AccountStatus.ACTIVE);
    }


}
