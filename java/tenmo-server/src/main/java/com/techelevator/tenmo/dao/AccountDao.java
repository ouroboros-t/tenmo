package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(String username) throws BalanceException;

    Long getAccountIdByUserId(Long userId) throws AccountNotFoundException, javax.security.auth.login.AccountNotFoundException;


}
