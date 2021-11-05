package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer createTransfer(Transfer transfer);

    boolean accountTransaction(Transfer transfer);

    List<Transfer> getUserTransfers();


}
