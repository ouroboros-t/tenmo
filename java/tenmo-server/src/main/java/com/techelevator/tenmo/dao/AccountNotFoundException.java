package com.techelevator.tenmo.dao;

public class AccountNotFoundException extends Exception{

    public AccountNotFoundException(String message){
        super(message);
    }
}
