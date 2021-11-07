package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDetail;

import java.util.List;

public interface TransferDao {

    Transfer createTransfer(Transfer transfer);

    boolean accountTransaction(Transfer transfer);

    List<TransferDetail> getTransferDetails(String username);


}
