package com.techelevator.tenmo.model;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Transfer {
    private Long transferId;
    @NotEmpty
    private int transferTypeId;
    @NotEmpty
    private int transferStatusId;
    @NotEmpty
    private Long accountFromId;
    @NotEmpty
    private Long accountToId;
    @NotEmpty
    private BigDecimal amount;

    public Transfer() {
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Long getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(Long accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Long getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(Long accountToId) {
        this.accountToId = accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
