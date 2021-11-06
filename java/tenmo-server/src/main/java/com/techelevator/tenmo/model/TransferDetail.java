package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class TransferDetail {
    @JsonProperty("transfer_id")
    @NotBlank
    private Integer transferId;
    @JsonProperty("amount")
    @NotBlank
    private double amount;
    @JsonProperty("user_from_id")
    @NotBlank
    private Integer userFromId;
    @JsonProperty("user_from_username")
    @NotBlank
    private String userFromUsername;
    @JsonProperty("user_to_id")
    @NotBlank
    private Integer userToId;
    @JsonProperty("user_to_username")
    @NotBlank
    private String userToUsername;
    @JsonProperty("transfer_status_id")
    @NotBlank
    private Integer transferStatusId;
    @JsonProperty("transfer_status_desc")
    @NotBlank
    private String transferStatusDesc;
    @JsonProperty("transfer_type_id")
    @NotBlank
    private Integer transferTypeId;
    @JsonProperty("transfer_type_desc")
    @NotBlank
    private String transferTypeDesc;

    public TransferDetail(){}

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(Integer userFromId) {
        this.userFromId = userFromId;
    }

    public String getUserFromUsername() {
        return userFromUsername;
    }

    public void setUserFromUsername(String userFromUsername) {
        this.userFromUsername = userFromUsername;
    }

    public Integer getUserToId() {
        return userToId;
    }

    public void setUserToId(Integer userToId) {
        this.userToId = userToId;
    }

    public String getUserToUsername() {
        return userToUsername;
    }

    public void setUserToUsername(String userToUsername) {
        this.userToUsername = userToUsername;
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
}
