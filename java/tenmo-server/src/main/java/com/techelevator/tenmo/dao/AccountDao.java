package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface AccountDao {

    double getBalance(String username) throws BalanceException;

    Integer getAccountIdByUserId(Integer userId) throws AccountNotFoundException, javax.security.auth.login.AccountNotFoundException;

    Account getAccount(String username) throws javax.security.auth.login.AccountNotFoundException;
}
