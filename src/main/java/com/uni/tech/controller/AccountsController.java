package com.uni.tech.controller;

import com.uni.tech.dto.CreateAccountRequest;
import com.uni.tech.dto.DeactivateRequest;
import com.uni.tech.dto.TransferRequest;
import com.uni.tech.entity.Account;
import com.uni.tech.entity.User;
import com.uni.tech.repository.UserRepository;
import com.uni.tech.service.AccountService;
import com.uni.tech.util.LoggedinUser;
import com.uni.tech.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/rest/accounts")
public class AccountsController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?>  ListActiveAccounts()
    {
      List<Account> accounts=  accountService.listActiveAccounts(GetLoggedinUser());
      return new ResponseEntity<>( ResponseUtil.createResponse(accounts.stream().map(a-> a.getAccountNumber()).collect(Collectors.toList())), HttpStatus.OK);
    }


    @PostMapping("/makeTransfer")
    public ResponseEntity<?> makeTransfer(@RequestBody TransferRequest transferRequest) {
        accountService.makeTransfer(GetLoggedinUser(),transferRequest.getSourceAccountNumber(), transferRequest.getTargetAccountNumber(), transferRequest.getAmount());
        return new ResponseEntity<>( ResponseUtil.createResponse("Transferred successfully"), HttpStatus.OK);
    }
    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request)
    {
        accountService.createAccount(GetLoggedinUser(), request.getBalance());
        return new ResponseEntity<>( ResponseUtil.createResponse("Account created"), HttpStatus.OK);
    }

    @PostMapping("/deactivateAccount")
    public ResponseEntity<?> deactivateAccount(@RequestBody DeactivateRequest request)
    {
        accountService.deactivateAccount(GetLoggedinUser(),request.getAccountNumber());
        return new ResponseEntity<>( ResponseUtil.createResponse("Deactivated successfully"), HttpStatus.OK);
    }
    private User GetLoggedinUser()
    {
        return userRepository.findByPin(LoggedinUser.getPin());
    }


}