package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Account {

    @JsonProperty("account_id")
    private int accountId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("balance")
    private BigDecimal balance;

    public Account() {
    }

    public Integer getAccountId() {
        return accountId;
    }
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
