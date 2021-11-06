package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferDetail {
    @JsonProperty("transfer_id")
    private Integer transferId;

    @JsonProperty("transfer_type_id")
    private Integer transferTypeId;

    @JsonProperty("transfer_type_desc")
    private String transferTypeDesc;

    @JsonProperty("transfer_status_id")
    private Integer transferStatusId;

    @JsonProperty("transfer_status_desc")
    private String transferStatusDesc;

    @JsonProperty("account_from")
    private Integer accountFromId;

    @JsonProperty("account_to")
    private Integer accountToId;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("balance")
    private double balance;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("username")
    private String username;

    public TransferDetail(){}

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Integer getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Integer transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public Integer getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Integer transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public Integer getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(Integer accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Integer getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(Integer accountToId) {
        this.accountToId = accountToId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
