package com.techelevator.tenmo.dao;

public class BalanceNotFoundException extends Exception {
    public BalanceNotFoundException(String message) {
        super(message);
    }
}
