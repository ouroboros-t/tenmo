package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDetail;

import java.util.List;

public interface TransferDao {

    Transfer createTransfer(Transfer transfer);

    boolean accountTransaction(Transfer transfer);

    //TODO: CLEANUP:: THIS METHOD BELOW IS NOT USED:::
    List<Transfer> getUserTransfers(String username);

    List<TransferDetail> getTransferDetails(String username);


}
