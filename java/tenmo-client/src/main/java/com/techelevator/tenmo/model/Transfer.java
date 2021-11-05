package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Transfer {

    @JsonProperty("transfer_id")
    private Integer transferId;
    @JsonProperty("transfer_type_id")
    private Integer transferTypeId;
    @JsonProperty("transfer_status_id")
    private Integer transferStatusId;
    @JsonProperty("account_from")
    private Integer accountFromId;
    @JsonProperty("account_to")
    private Integer accountToId;
    @JsonProperty("amount")
    private BigDecimal amount;

    public Transfer(){}

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public Integer getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Integer getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Integer getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Integer getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
