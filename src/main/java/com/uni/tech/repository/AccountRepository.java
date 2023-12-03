package com.uni.tech.repository;

import com.uni.tech.entity.Account;
import com.uni.tech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);

    List<Account> findByUserAndAccountStatus(User user, Account.AccountStatus accountStatus);
}