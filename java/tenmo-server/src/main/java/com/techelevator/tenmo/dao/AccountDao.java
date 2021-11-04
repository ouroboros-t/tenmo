package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(String username) throws BalanceException;

    Long getAccountIdByUserId(Long userId) throws AccountNotFoundException, javax.security.auth.login.AccountNotFoundException;

}
