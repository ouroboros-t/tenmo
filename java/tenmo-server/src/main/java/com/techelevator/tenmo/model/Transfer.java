package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Transfer {

    @JsonProperty("transfer_id")
    private Integer transferId;
    @NotEmpty
    @JsonProperty("transfer_type_id")
    private Integer transferTypeId;
    @NotEmpty
    @JsonProperty("transfer_status_id")
    private Integer transferStatusId;
    @NotEmpty
    @JsonProperty("account_from")
    private Integer accountFromId;
    @NotEmpty
    @JsonProperty("account_to")
    private Integer accountToId;
    @NotEmpty
    @JsonProperty("amount")
    private BigDecimal amount;

    public Transfer() {
    }

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

    public Integer getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Integer transferStatusId) {
        this.transferStatusId = transferStatusId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
