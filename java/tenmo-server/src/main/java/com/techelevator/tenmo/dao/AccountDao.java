package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(String username) throws BalanceException;
}
